package net.axel.sharehope.repository;

import net.axel.sharehope.domain.dtos.category.CategoryProjectionDTO;
import net.axel.sharehope.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<CategoryProjectionDTO> findAllCategoryByOrderByIdDesc();

    Optional<CategoryProjectionDTO> findCategoryById(Long id);
}