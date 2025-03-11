package net.axel.sharehope.mapper;

import net.axel.sharehope.domain.dtos.action.ActionEmbeddedDTO;
import net.axel.sharehope.domain.dtos.category.CategoryProjectionDTO;
import net.axel.sharehope.domain.dtos.category.CategoryResponseDTO;
import net.axel.sharehope.domain.entities.Action;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.domain.entities.Donation;
import net.axel.sharehope.domain.entities.Need;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    /**
     * Maps a Category entity to CategoryResponseDTO.
     * The icon (from attachments) is provided from the service layer.
     */
    public CategoryResponseDTO fromEntityToResponse(Category category, String icon) {
        if (category == null) {
            return null;
        }
        return new CategoryResponseDTO(
                category.getId(),
                category.getCategoryName(),
                category.getDescription(),
                mapActions(category.getActions(), this::mapActionFromEntityToEmbeddedDTO),
                icon
        );
    }

    /**
     * Maps a CategoryProjectionDTO to CategoryResponseDTO.
     * The icon (from attachments) is provided from the service layer.
     */
    public CategoryResponseDTO fromProjectionToResponse(CategoryProjectionDTO category, String icon) {
        if (category == null) {
            return null;
        }
        return new CategoryResponseDTO(
                category.getId(),
                category.getCategoryName(),
                category.getDescription(),
                mapActions(category.getActions(), this::mapActionToEmbeddedDTO),
                icon
        );
    }

    /**
     * Generic method to map a list of actions using the provided mapper function.
     */
    private <T> List<ActionEmbeddedDTO> mapActions(List<T> actions, Function<T, ActionEmbeddedDTO> mapper) {
        if (actions == null || actions.isEmpty()) {
            return List.of();
        }
        return actions.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * Maps an Action projection (DTO) to ActionEmbeddedDTO.
     */
    private ActionEmbeddedDTO mapActionToEmbeddedDTO(CategoryProjectionDTO.ActionINestedDTO action) {
        return new ActionEmbeddedDTO(
                action.getId(),
                action.getAmount(),
                action.getDescription(),
                action.getCreatedAt(),
                determineActionType(action),
                determineActionStatus(action)
        );
    }

    /**
     * Maps an Action entity to ActionEmbeddedDTO.
     */
    private ActionEmbeddedDTO mapActionFromEntityToEmbeddedDTO(Action action) {
        return new ActionEmbeddedDTO(
                action.getId(),
                action.getAmount(),
                action.getDescription(),
                action.getCreatedAt(),
                determineActionType(action),
                determineActionStatus(action)
        );
    }

    /**
     * Determines the action type based on the instance.
     */
    private String determineActionType(Object action) {
        if (action instanceof Donation) {
            return "DONATION";
        } else if (action instanceof Need) {
            return "NEED";
        } else {
            return "UNKNOWN";
        }
    }

    /**
     * Determines the action status based on the instance.
     */
    private String determineActionStatus(Object action) {
        if (action instanceof Donation donation) {
            return donation.getDonationStatus() != null ? donation.getDonationStatus().toString() : null;
        } else if (action instanceof Need need) {
            return need.getNeedStatus() != null ? need.getNeedStatus().toString() : null;
        }
        return null;
    }
}
