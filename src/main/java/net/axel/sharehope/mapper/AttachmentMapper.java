package net.axel.sharehope.mapper;

import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentResponseDTO;
import net.axel.sharehope.domain.entities.Attachment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttachmentMapper extends BaseMapper<Attachment, AttachmentRequestDTO, AttachmentResponseDTO> {
}
