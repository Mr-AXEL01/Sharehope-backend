package net.axel.sharehope.service.impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentResponseDTO;
import net.axel.sharehope.domain.dtos.category.CategoryProjectionDTO;
import net.axel.sharehope.domain.dtos.category.CategoryRequestDTO;
import net.axel.sharehope.domain.dtos.category.CategoryResponseDTO;
import net.axel.sharehope.domain.entities.Attachment;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.CategoryMapper;
import net.axel.sharehope.repository.CategoryRepository;
import net.axel.sharehope.service.AttachmentService;
import net.axel.sharehope.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final String DEFAULT_CATEGORY_ICON = "https://res.cloudinary.com/dofubyjcd/image/upload/v1741649439/shareHope/categories/icons/defaultCategoryIcon.png";

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final AttachmentService attachmentService;


    @Override
    public List<CategoryResponseDTO> findAll() {
        List<CategoryProjectionDTO> categories = repository.findAllCategoryByOrderByIdDesc();

        return categories.stream()
                .map(category -> {
                    String icon = attachmentService.findAttachmentUrl("Category", category.getId());
                    if (icon == null) icon = DEFAULT_CATEGORY_ICON;
                    return mapper.fromProjectionToResponse(category, icon);
                })
                .toList();
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        CategoryProjectionDTO category = repository.findCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));

        String icon = attachmentService.findAttachmentUrl("Category", category.getId());
        if (icon == null) icon = DEFAULT_CATEGORY_ICON;
        return mapper.fromProjectionToResponse(category, icon);
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO requestDTO) {
        Category category = Category.createCategory(requestDTO.categoryName(), requestDTO.description());
        Category savedCategory = repository.save(category);

        return getCategoryResponseDTO(requestDTO, savedCategory, DEFAULT_CATEGORY_ICON);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO requestDTO) {
        Category existingCategory = getCategoryById(id);
        existingCategory.upadteCategory(requestDTO);
        String icon;
        if (existingCategory.getIcon() != null && !existingCategory.getIcon().isEmpty()) {
            icon = existingCategory.getIcon();
        } else {
            icon = DEFAULT_CATEGORY_ICON;
        }

        return getCategoryResponseDTO(requestDTO, existingCategory, icon);
    }

    private CategoryResponseDTO getCategoryResponseDTO(CategoryRequestDTO requestDTO, Category category, String icon) {
        if (requestDTO.icon() != null && !requestDTO.icon().isEmpty()) {
            AttachmentRequestDTO iconDTO = new AttachmentRequestDTO(
                    requestDTO.icon(),
                    "Category",
                    category.getId(),
                    "categories/icons"
            );
            AttachmentResponseDTO attachment = attachmentService.createAttachment(iconDTO);
            icon = attachment.filePath();
            category.setIcon(icon);
        }

        return mapper.fromEntityToResponse(category, icon);
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("There is no category to remove with the ID: " + id);
        }

        Attachment attachment = attachmentService.findAttachment("Category", id);
        if (attachment != null) {
            attachmentService.deleteAttachment(attachment.getId(), "categories/icons/");
        }

        repository.deleteById(id);
    }

    private Category getCategoryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }
}
