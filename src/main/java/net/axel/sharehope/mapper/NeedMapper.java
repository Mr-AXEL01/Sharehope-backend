package net.axel.sharehope.mapper;

import net.axel.sharehope.domain.dtos.action.need.NeedResponseDTO;
import net.axel.sharehope.domain.dtos.category.CategoryEmbeddedDTO;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.domain.entities.Need;
import net.axel.sharehope.security.domain.dto.user.UserEmbeddedDTO;
import net.axel.sharehope.security.domain.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class NeedMapper {

    public NeedResponseDTO toResponse(Need need) {
        return new NeedResponseDTO(
                need.getId(),
                need.getAmount(),
                need.getDescription(),
                need.getNeedStatus(),
                mapCategory(need.getCategory()),
                mapUser(need.getUser()),
                need.getCreatedAt(),
                need.getAttachments()
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
