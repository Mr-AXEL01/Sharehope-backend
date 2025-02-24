package net.axel.sharehope.domain.dtos.category;

import net.axel.sharehope.domain.dtos.action.ActionEmbeddedDTO;

import java.util.List;

public record CategoryResponseDTO(
        Long id,
        String categoryName,
        String description,
        List<ActionEmbeddedDTO> actions
) {
}
