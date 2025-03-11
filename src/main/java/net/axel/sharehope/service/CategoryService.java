package net.axel.sharehope.service;

import net.axel.sharehope.domain.dtos.category.CategoryRequestDTO;
import net.axel.sharehope.domain.dtos.category.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> findAll();

    CategoryResponseDTO findById(Long id);

    CategoryResponseDTO create(CategoryRequestDTO requestDTO);

    CategoryResponseDTO update(Long id, CategoryRequestDTO requestDTO);

    void delete(Long id);
}
