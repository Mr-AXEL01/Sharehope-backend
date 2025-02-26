package net.axel.sharehope.service.impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.category.CategoryProjectionDTO;
import net.axel.sharehope.domain.dtos.category.CategoryRequestDTO;
import net.axel.sharehope.domain.dtos.category.CategoryResponseDTO;
import net.axel.sharehope.domain.entities.Article;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.CategoryMapper;
import net.axel.sharehope.repository.CategoryRepository;
import net.axel.sharehope.service.CategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;


    @Override
    public List<CategoryProjectionDTO> findAll() {
        return repository.findAllCategoryByOrderByIdDesc();
    }

    @Override
    public CategoryProjectionDTO findById(Long id) {
        return repository.findCategoryById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category", id));
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO requestDTO) {
        Category category = Category.createCategory(requestDTO.categoryName(), requestDTO.description());

//        TODO: handle the add of an icon with the polymorphic to store it in attachments.

        Category savedCategory = repository.save(category);
        return mapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO requestDTO) {
        Category existingCategory = getCategoryById(id);
        existingCategory.upadteCategory(requestDTO);

//        TODO: update the icon.

        return mapper.toResponse(existingCategory);
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("There is no category to remove with the ID: " + id);
        }
        repository.deleteById(id);
    }

    private Category getCategoryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }
}
