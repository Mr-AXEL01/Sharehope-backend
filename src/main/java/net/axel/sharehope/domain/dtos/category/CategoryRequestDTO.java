package net.axel.sharehope.domain.dtos.category;

import jakarta.validation.constraints.NotBlank;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.validation.IsUnique;
import org.springframework.web.multipart.MultipartFile;

public record CategoryRequestDTO(

        @IsUnique(entityClass = Category.class, fieldName = "categoryName", message = "There is already a category with this name!")
        @NotBlank String categoryName,

        @NotBlank String description,

        MultipartFile icon
) {
}
