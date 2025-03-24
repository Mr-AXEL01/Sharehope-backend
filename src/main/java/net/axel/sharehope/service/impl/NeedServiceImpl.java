package net.axel.sharehope.service.impl;

import net.axel.sharehope.domain.dtos.action.ActionCreateDTO;
import net.axel.sharehope.domain.dtos.action.ActionStatusDTO;
import net.axel.sharehope.domain.dtos.action.ActionUpdateDTO;
import net.axel.sharehope.domain.dtos.action.need.NeedResponseDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.domain.entities.Need;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.NeedMapper;
import net.axel.sharehope.repository.NeedRepository;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.service.AttachmentService;
import net.axel.sharehope.service.CategoryService;
import net.axel.sharehope.service.NeedService;
import net.axel.sharehope.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class NeedServiceImpl extends ActionServiceDefault implements NeedService {

    private final NeedRepository repository;
    private final NeedMapper mapper;

    public NeedServiceImpl(CategoryService categoryService, UserService userService, AttachmentService attachmentService, NeedRepository repository, NeedMapper mapper) {
        super(categoryService, userService, attachmentService);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public NeedResponseDTO createNeed(ActionCreateDTO dto) {
        Category category = getCategory(dto.categoryId());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = getUser(username);

        Need need = Need.createNeed(dto.amount(), dto.description(), category, user);
        Need savedNeed = repository.save(need);

        if (dto.attachments() != null && !dto.attachments().isEmpty()) {
            processAttachments(dto.attachments(), savedNeed);
        }

        return mapper.toResponse(savedNeed);
    }

    @Override
    public NeedResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(need -> {
                    List<String> attachments = attachmentService.findAttachmentUrls("Need", need.getId());
                    need.setAttachments(attachments)
                            .setUser(getUser(need.getUser().getUsername()));
                    return mapper.toResponse(need);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Need", id));
    }

    @Override
    public Page<NeedResponseDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Need> needsPage = repository.findAllByOrderByCreatedAtDesc(pageable);

        return needsPage.map(need -> {
            List<String> attachments = attachmentService.findAttachmentUrls("Need", need.getId());
            need.setAttachments(attachments)
                    .setUser(getUser(need.getUser().getUsername()));
            return mapper.toResponse(need);
        });
    }

    @Override
    public List<NeedResponseDTO> findAllMyNeed(int page, int size) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = getUser(username);
        Pageable pageable = PageRequest.of(page, size);
        Page<Need> needs = repository.findAllByUserOrderByCreatedAtDesc(user, pageable);

        return needs.getContent().stream()
                .map(need -> {
                    List<String> attachments = attachmentService.findAttachmentUrls("Need", need.getId());
                    need.setAttachments(attachments);
                    return mapper.toResponse(need);
                })
                .toList();
    }

    @Override
    public NeedResponseDTO update(Long id, ActionUpdateDTO dto) {
        Need existingNeed = getNeed(id);
        existingNeed.updateNeed(dto.description(), getCategory(dto.categoryId()));
        existingNeed.setUser(getUser(existingNeed.getUser().getUsername()));

        if(dto.attachments() != null && !dto.attachments().isEmpty()) {
            processAttachments(dto.attachments(), existingNeed);
        } else {
            List<String> attachments = attachmentService.findAttachmentUrls("Need", id);
            existingNeed.setAttachments(attachments);
        }
        
        return mapper.toResponse(existingNeed);
    }

    @Override
    public NeedResponseDTO updateStatus(Long id, ActionStatusDTO statusDTO) {
        Need need = getNeed(id);
        need.updateStatus(statusDTO);
        need.setUser(getUser(need.getUser().getUsername()));
        List<String> attachments = attachmentService.findAttachmentUrls("Need", id);
        need.setAttachments(attachments);
        return mapper.toResponse(need);
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("There is no need to remove with the ID: " + id);
        }

        attachmentService.findAttachments("Need", id)
                .forEach(attachment ->
                        attachmentService.deleteAttachment(attachment.getId(), "needs")
                );

        repository.deleteById(id);
    }

    private Need getNeed(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Need", id));
    }

    private void processAttachments(List<MultipartFile> attachments, Need need) {
        if (attachments != null && !attachments.isEmpty()) {
            List<String> attachmentPaths = attachments.stream()
                    .map(att -> {
                        AttachmentRequestDTO attachmentDTO = new AttachmentRequestDTO(
                                att,
                                "Need",
                                need.getId(),
                                "needs"
                        );
                        return attachmentService.createAttachment(attachmentDTO).filePath();
                    })
                    .toList();
            need.setAttachments(attachmentPaths);
        }
    }
}
