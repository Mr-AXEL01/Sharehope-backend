package net.axel.sharehope.service;

import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentResponseDTO;
import net.axel.sharehope.domain.entities.Attachment;

public interface AttachmentService {

    AttachmentResponseDTO createAttachment(AttachmentRequestDTO dto);

    String findAttachmentUrl(String attachableType, Long attachableId);

    Attachment findAttachment(String attachableType, Long attachableId);

    void deleteAttachment(Long attachmentId, String folderPath);
}
