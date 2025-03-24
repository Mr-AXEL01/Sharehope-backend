package net.axel.sharehope.domain.dtos.action.need;

import net.axel.sharehope.domain.dtos.category.CategoryEmbeddedDTO;
import net.axel.sharehope.domain.enums.NeedStatus;
import net.axel.sharehope.security.domain.dto.user.UserEmbeddedDTO;

import java.time.Instant;
import java.util.List;

public record NeedResponseDTO(
        Long id,
        Double amount,
        String description,
        NeedStatus needStatus,
        CategoryEmbeddedDTO category,
        UserEmbeddedDTO user,
        Instant createdAt,
        List<String> attachmentUrls
) {
}
