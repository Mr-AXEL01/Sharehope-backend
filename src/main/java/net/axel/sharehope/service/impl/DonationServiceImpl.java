package net.axel.sharehope.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.axel.sharehope.domain.dtos.action.ActionCreateDTO;
import net.axel.sharehope.domain.dtos.action.ActionStatusDTO;
import net.axel.sharehope.domain.dtos.action.ActionUpdateDTO;
import net.axel.sharehope.domain.dtos.action.donation.DonationResponseDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.domain.entities.Donation;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.DonationMapper;
import net.axel.sharehope.repository.DonationRepository;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.service.AttachmentService;
import net.axel.sharehope.service.CategoryService;
import net.axel.sharehope.service.DonationService;
import net.axel.sharehope.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    public DonationResponseDTO createDonation(ActionCreateDTO dto) throws StripeException {
        Category category = getCategory(dto.categoryId());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = getUser(username);

        Donation donation = Donation.createDonation(dto.amount(), dto.description(), category, user);

        String paymentIntentClientSecret = processPayment(dto.amount());

        Donation savedDonation = repository.save(donation);

        processAttachments(dto.attachments(), savedDonation);

        return mapper.toResponse(savedDonation, paymentIntentClientSecret);
    }

    @Override
    public DonationResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(donation -> {
                    List<String> attachments = attachmentService.findAttachmentUrls("Donation", donation.getId());
                    donation.setAttachments(attachments)
                            .setUser(getUser(donation.getUser().getUsername()));
                    return mapper.toResponse(donation, null);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Donation", id));
    }

    @Override
    public Page<DonationResponseDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Donation> donationPage = repository.findAllByOrderByCreatedAtDesc(pageable);

        return donationPage.map(donation -> {
            List<String> attachments = attachmentService.findAttachmentUrls("Donation", donation.getId());
            donation.setAttachments(attachments)
                    .setUser(getUser(donation.getUser().getUsername()));
            return mapper.toResponse(donation, null);
        });
    }

    @Override
    public List<DonationResponseDTO> findAllMyDonation(int page, int size) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = getUser(username);
        Pageable pageable = PageRequest.of(page, size);
        Page<Donation> donations = repository.findAllByUserOrderByCreatedAtDesc(user, pageable);

        return donations.getContent().stream()
                .map(donation -> {
                    List<String> attachments = attachmentService.findAttachmentUrls("Donation", donation.getId());
                    donation.setAttachments(attachments);
                    return mapper.toResponse(donation, null);
                })
                .toList();
    }

    @Override
    public DonationResponseDTO update(Long id, ActionUpdateDTO dto) {
        Donation existingDonation = getDonation(id);
        existingDonation.updateDonation(dto.description(), getCategory(dto.categoryId()));
        existingDonation.setUser(getUser(existingDonation.getUser().getUsername()));

        if(dto.attachments() != null && !dto.attachments().isEmpty()) {
            processAttachments(dto.attachments(), existingDonation);
        } else {
            List<String> attachments = attachmentService.findAttachmentUrls("Donation", id);
            existingDonation.setAttachments(attachments);
        }
        return mapper.toResponse(existingDonation, null);
    }

    public DonationResponseDTO updateStatus(Long id, ActionStatusDTO statusDTO) {
        Donation donation = getDonation(id);
        donation.updateStatus(statusDTO);
        donation.setUser(getUser(donation.getUser().getUsername()));
        List<String> attachments = attachmentService.findAttachmentUrls("Donation", id);
        donation.setAttachments(attachments);
        return mapper.toResponse(donation, null);
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("There is no donation to remove with the ID: " + id);
        }

        attachmentService.findAttachments("Donation", id)
                .forEach(attachment ->
                        attachmentService.deleteAttachment(attachment.getId(), "donations")
                );

        repository.deleteById(id);
    }

    private String processPayment(Double amount) throws StripeException {
        if (amount > 0) {
            PaymentIntent paymentIntent = stripeService.processPayment(amount);
            log.info("Stripe Payment Successful: {}", paymentIntent.getId());
            return paymentIntent.getClientSecret();
        }
        return null;
    }

    private void processAttachments(List<MultipartFile> attachments, Donation donation) {
        if (attachments != null && !attachments.isEmpty()) {
            List<String> attachmentPaths = attachments.stream()
                    .map(att -> {
                        AttachmentRequestDTO attachmentDTO = new AttachmentRequestDTO(
                                att,
                                "Donation",
                                donation.getId(),
                                "donations"
                        );
                        return attachmentService.createAttachment(attachmentDTO).filePath();
                    })
                    .toList();
            donation.setAttachments(attachmentPaths);
        }
    }

    private Category getCategory(Long id) {
        return categoryService.findEntityById(id);
    }

    private AppUser getUser(String username) {
        return userService.findUserEntity(username);
    }

    private Donation getDonation(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation", id));
    }
}
