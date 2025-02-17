package net.axel.sharehope.domain.dtos.attachment;

import java.time.Instant;

/**
 * Projection for {@link net.axel.sharehope.domain.entities.Attachment}
 */
public interface AttachmentResponseDTO {
    Long getId();

    String getFilePath();

    String getFileType();

    Instant getUploadDate();

    Long getAttachableId();

    String getAttachableType();
}