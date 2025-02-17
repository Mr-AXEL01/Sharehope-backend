package net.axel.sharehope.domain.dtos.action.need;

import net.axel.sharehope.domain.enums.NeedStatus;

import java.time.Instant;

/**
 * Projection for {@link net.axel.sharehope.domain.entities.Need}
 */
public interface NeedResponseDTO {
    Long getId();

    Double getAmount();

    String getDescription();

    Instant getCreatedAt();

    NeedStatus getNeedStatus();

    CategoryNestedDTO getCategory();

    /**
     * Projection for {@link net.axel.sharehope.domain.entities.Category}
     */
    interface CategoryNestedDTO {
        Long getId();

        String getCategoryName();

        String getDescription();
    }
}