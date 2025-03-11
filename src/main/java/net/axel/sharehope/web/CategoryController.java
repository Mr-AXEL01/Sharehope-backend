package net.axel.sharehope.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.category.CategoryProjectionDTO;
import net.axel.sharehope.domain.dtos.category.CategoryRequestDTO;
import net.axel.sharehope.domain.dtos.category.CategoryResponseDTO;
import net.axel.sharehope.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CategoryController.CONTROLLER_PATH)

@RequiredArgsConstructor
public class CategoryController {

    public final static String CONTROLLER_PATH = "api/v1/categories";

    private final CategoryService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@ModelAttribute @Valid CategoryRequestDTO requestDTO) {
        CategoryResponseDTO category = service.create(requestDTO);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {
        List<CategoryResponseDTO> categories = service.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable("id") Long id) {
        CategoryResponseDTO category = service.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid CategoryRequestDTO requestDTO
    ) {
        CategoryResponseDTO updatedCategory = service.update(id, requestDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
