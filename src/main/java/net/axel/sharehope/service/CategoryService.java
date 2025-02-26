package net.axel.sharehope.service;

import net.axel.sharehope.domain.dtos.category.CategoryProjectionDTO;
import net.axel.sharehope.domain.dtos.category.CategoryRequestDTO;
import net.axel.sharehope.domain.dtos.category.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryProjectionDTO> findAll();

    CategoryProjectionDTO findById(Long id);

    CategoryResponseDTO create(CategoryRequestDTO requestDTO);

    CategoryResponseDTO update(Long id, CategoryRequestDTO requestDTO);

    void delete(Long id);
}
