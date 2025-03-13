package net.axel.sharehope.mapper;

import net.axel.sharehope.domain.dtos.action.donation.DonationResponseDTO;
import net.axel.sharehope.domain.dtos.category.CategoryEmbeddedDTO;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.domain.entities.Donation;
import net.axel.sharehope.security.domain.dto.user.UserEmbeddedDTO;
import net.axel.sharehope.security.domain.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class DonationMapper {

    public DonationResponseDTO toResponse(Donation donation) {
//        System.out.println("\n============" + donation.getUser().getAvatar()+ "=========\n");
        return new DonationResponseDTO(
                donation.getId(),
                donation.getAmount(),
                donation.getDescription(),
                donation.getDonationStatus(),
                mapCategory(donation.getCategory()),
                mapUser(donation.getUser()),
                donation.getCreatedAt(),
                donation.getAttachments()
        );
    }

    private CategoryEmbeddedDTO mapCategory(Category category) {
        return new CategoryEmbeddedDTO(
                category.getId(),
                category.getCategoryName(),
                category.getDescription()
        );
    }

    private UserEmbeddedDTO mapUser(AppUser user) {
        return new UserEmbeddedDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getAvatar()
        );
    }
}
