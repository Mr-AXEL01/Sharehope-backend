package net.axel.sharehope.domain.dtos.action.donation;

import net.axel.sharehope.domain.dtos.category.CategoryEmbeddedDTO;
import net.axel.sharehope.domain.enums.DonationStatus;
import net.axel.sharehope.security.domain.dto.user.UserEmbeddedDTO;

import java.time.Instant;
import java.util.List;

public record DonationResponseDTO(
        Long id,
        Double amount,
        String description,
        DonationStatus donationStatus,
        CategoryEmbeddedDTO category,
        UserEmbeddedDTO user,
        Instant createdAt,
        List<String> attachmentUrls,
        String paymentIntentClientSecret
) {
}
