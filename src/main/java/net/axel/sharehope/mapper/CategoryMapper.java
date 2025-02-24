package net.axel.sharehope.mapper;

import net.axel.sharehope.domain.dtos.action.ActionEmbeddedDTO;
import net.axel.sharehope.domain.dtos.category.CategoryRequestDTO;
import net.axel.sharehope.domain.dtos.category.CategoryResponseDTO;
import net.axel.sharehope.domain.entities.Action;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.domain.entities.Donation;
import net.axel.sharehope.domain.entities.Need;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { ActionMapper.class })
public interface CategoryMapper extends BaseMapper<Category, CategoryRequestDTO, CategoryResponseDTO> {

    @Mapping(target = "actions", source = "actions")
    @Override
    CategoryResponseDTO toResponse(Category category);

    default List<ActionEmbeddedDTO> mapActions(List<Action> actions) {
        if (actions == null) {
            return null;
        }
        return actions.stream()
                .map(this::toActionEmbeddedDTO)
                .collect(Collectors.toList());
    }

    default ActionEmbeddedDTO toActionEmbeddedDTO(Action action) {
        return new ActionEmbeddedDTO(
                action.getId(),
                action.getAmount(),
                action.getDescription(),
                action.getCreatedAt(),
                getActionType(action),
                getActionStatus(action)
        );
    }

    private String getActionType(Action action) {
        return (action instanceof Donation) ? "DONATION" :
                (action instanceof Need) ? "NEED" :
                        "UNKNOWN";
    }

    private String getActionStatus(Action action) {
        return (action instanceof Donation donation)
                ? (donation.getDonationStatus() != null ? donation.getDonationStatus().toString() : null)
                : (action instanceof Need need)
                ? (need.getNeedStatus() != null ? need.getNeedStatus().toString() : null)
                : null;
    }
}
