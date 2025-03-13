package net.axel.sharehope.service.impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentResponseDTO;
import net.axel.sharehope.domain.entities.Attachment;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.AttachmentMapper;
import net.axel.sharehope.repository.AttachmentRepository;
import net.axel.sharehope.service.AttachmentService;
import net.axel.sharehope.service.FileUploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public String findAttachmentUrl(String attachableType, Long attachableId) {
        return repository.findByAttachableTypeAndAttachableId(attachableType, attachableId)
                .map(Attachment::getFilePath)
                .orElse(null);
    }

    @Override
    public List<String> findAttachmentUrls(String attachableType, Long attachableId) {
        return repository.findAllByAttachableTypeAndAttachableId(attachableType, attachableId)
                .stream()
                .map(Attachment::getFilePath)
                .toList();
    }

    @Override
    public Attachment findAttachment(String attachableType, Long attachableId) {
        return repository.findByAttachableTypeAndAttachableId(attachableType, attachableId)
                .orElse(null);
    }

    @Override
    public List<Attachment> findAttachments(String attachableType, Long attachableId) {
        return repository.findAllByAttachableTypeAndAttachableId(attachableType, attachableId)
                .stream()
                .toList();
    }

    @Override
    public void deleteAttachment(Long attachmentId, String folderPath) {
        Attachment attachment = repository.findById(attachmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment", attachmentId));

        String fileUrl = attachment.getFilePath();
        String publicId = "shareHope/"+ folderPath + extractPublicIdFromUrl(fileUrl);

        fileUploader.delete(publicId, attachment.getFileType());

        repository.delete(attachment);
    }


    private String extractPublicIdFromUrl(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return null;
        }

        String[] parts = fileUrl.split("/");
        String lastPart = parts[parts.length - 1];
        int dotIndex = lastPart.lastIndexOf('.');
        return (dotIndex != -1) ? lastPart.substring(0, dotIndex) : lastPart;
    }
}
