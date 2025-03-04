package net.axel.sharehope.domain.dtos.attachment;

import org.springframework.web.multipart.MultipartFile;

public record AttachmentRequestDTO(
        MultipartFile file,
        String attachableType,
        Long attachableId,
        String folderPath
) {
}
