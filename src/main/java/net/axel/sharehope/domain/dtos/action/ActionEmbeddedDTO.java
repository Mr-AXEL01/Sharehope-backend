package net.axel.sharehope.domain.dtos.action;

import java.time.Instant;

public record ActionEmbeddedDTO(
        Long id,
        Double amount,
        String description,
        Instant createdAt,
        String actionType,
        String status
) {
}
