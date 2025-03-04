package net.axel.sharehope.service.impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentResponseDTO;
import net.axel.sharehope.domain.entities.Attachment;
import net.axel.sharehope.mapper.AttachmentMapper;
import net.axel.sharehope.repository.AttachmentRepository;
import net.axel.sharehope.service.AttachmentService;
import net.axel.sharehope.service.FileUploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository repository;
    private final FileUploader fileUploader;
    private final AttachmentMapper mapper;

    @Override
    public AttachmentResponseDTO createAttachment(AttachmentRequestDTO dto) {
        String fileUrl = fileUploader.upload(dto.file(), dto.folderPath());
        Attachment attachment = Attachment.createAttachment(fileUrl, dto.file().getContentType(), dto.attachableId(), dto.attachableType());
        Attachment savedAttachment = repository.save(attachment);
        return mapper.toResponse(savedAttachment);
    }

    @Override
    public String findAttachment(String attachableType, Long attachableId) {
        Attachment attachment = repository.findByAttachableTypeAndAttachableId(attachableType, attachableId);
        return attachment.getFilePath();
    }
}
