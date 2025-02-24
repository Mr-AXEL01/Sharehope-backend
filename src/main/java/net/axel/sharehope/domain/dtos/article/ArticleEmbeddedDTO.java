package net.axel.sharehope.domain.dtos.article;

import java.time.Instant;

public record ArticleEmbeddedDTO(
        Long id,
        String title,
        String description,
        String content,
        Instant createdAt
) {
}
