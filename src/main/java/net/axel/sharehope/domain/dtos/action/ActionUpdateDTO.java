package net.axel.sharehope.domain.dtos.action;

import jakarta.validation.constraints.NotNull;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.validation.Exists;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ActionUpdateDTO(
        String description,

        @Exists(entityClass = Category.class, fieldName = "id", message = "Category does not exists.")
        @NotNull Long categoryId,

        List<MultipartFile> attachments
) {
}
