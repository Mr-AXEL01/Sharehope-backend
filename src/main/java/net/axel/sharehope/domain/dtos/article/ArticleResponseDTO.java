package net.axel.sharehope.domain.dtos.article;

import net.axel.sharehope.security.domain.dto.user.UserEmbeddedDTO;

import java.time.Instant;

public record ArticleResponseDTO(
        Long id,
        String title,
        String description,
        String content,
        Instant createdAt,
        UserEmbeddedDTO author,
        String[] attachments
) {
}
