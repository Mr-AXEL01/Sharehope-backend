package net.axel.sharehope.service;

import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentResponseDTO;

public interface AttachmentService {

    AttachmentResponseDTO createAttachment(AttachmentRequestDTO dto);

    String findAttachment(String attachableType, Long attachableId);
}
