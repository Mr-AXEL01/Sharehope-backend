package net.axel.sharehope.service.impl;

import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.axel.sharehope.domain.dtos.action.ActionRequestDTO;
import net.axel.sharehope.domain.dtos.action.donation.DonationResponseDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentResponseDTO;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.domain.entities.Donation;
import net.axel.sharehope.mapper.DonationMapper;
import net.axel.sharehope.repository.DonationRepository;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.security.service.UserService;
import net.axel.sharehope.service.AttachmentService;
import net.axel.sharehope.service.CategoryService;
import net.axel.sharehope.service.DonationService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository repository;
    private final DonationMapper mapper;
    private final CategoryService categoryService;
    private final UserService userService;
    private final StripeService stripeService;
    private final AttachmentService attachmentService;


    @Override
    public DonationResponseDTO createDonation(ActionRequestDTO dto) throws StripeException {
        Category category = getCategory(dto.categoryId());
        AppUser user = getAuthenticatedUser();

        Donation donation = Donation.createDonation(dto.amount(), dto.description(), category, user);

        processPayment(dto.amount());

        processAttachments(dto, donation);

        Donation savedDonation = repository.save(donation);
        return mapper.toResponse(savedDonation);
    }

    private void processPayment(Double amount) throws StripeException {
        if (amount > 0) {
            try {
                String paymentIntentId = stripeService.processPayment(amount);
                log.info("Stripe Payment Successful: {}", paymentIntentId);
            } catch (StripeException e) {
                log.error("Stripe Payment Failed: {}", e.getMessage());
                throw e;
            }
        }
    }

    private void processAttachments(ActionRequestDTO dto, Donation donation) {
        if (dto.attachments() != null && !dto.attachments().isEmpty()) {
            List<String> attachmentPaths = dto.attachments().stream()
                    .map(att -> {
                        AttachmentRequestDTO attachmentDTO = new AttachmentRequestDTO(
                                att, "Donation", donation.getId(), "donations"
                        );
                        AttachmentResponseDTO attachment = attachmentService.createAttachment(attachmentDTO);
                        return attachment.filePath();
                    })
                    .toList();
            donation.setAttachments(attachmentPaths);
        }
    }

    private Category getCategory(Long id) {
        return categoryService.findEntityById(id);
    }

    private AppUser getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = userService.findUserEntity(username);
        String userAvatar = attachmentService.findAttachmentUrl("AppUser", user.getId());
        if (userAvatar != null) user.setAvatar(userAvatar);
        return user;
    }
}
