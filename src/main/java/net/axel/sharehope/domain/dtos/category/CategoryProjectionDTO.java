package net.axel.sharehope.domain.dtos.category;

import java.time.Instant;
import java.util.List;

/**
 * Projection for {@link net.axel.sharehope.domain.entities.Category}
 */
public interface CategoryProjectionDTO {
    Long getId();

    String getCategoryName();

    String getDescription();

    List<ActionINestedDTO> getActions();

    /**
     * Projection for {@link net.axel.sharehope.domain.entities.Action}
     */
    interface ActionINestedDTO {
        Long getId();

        Double getAmount();

        String getDescription();

        Instant getCreatedAt();
    }
}