package net.axel.sharehope.domain.dtos.action.donation;

import net.axel.sharehope.domain.dtos.action.need.NeedResponseDTO;
import net.axel.sharehope.domain.enums.DonationStatus;

import java.time.Instant;

/**
 * Projection for {@link net.axel.sharehope.domain.entities.Donation}
 */
public interface DonationResponseDTO {
    Long getId();

    Double getAmount();

    String getDescription();

    Instant getCreatedAt();

    DonationStatus getDonationStatus();

    NeedResponseDTO.CategoryNestedDTO getCategory();
}