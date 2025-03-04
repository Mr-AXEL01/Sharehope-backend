package net.axel.sharehope.domain.dtos.attachment;

import java.time.Instant;

public record AttachmentResponseDTO(
        Long id,
        String filePath,
        String fileType,
        Instant uploadDate,
        Long attachableId,
        String attachableType
) {
}
