package net.axel.sharehope.domain.dtos.action;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.validation.Exists;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ActionCreateDTO(

        @PositiveOrZero Double amount,

        @NotBlank String description,

        @Exists(entityClass = Category.class, fieldName = "id", message = "Category does not exists.")
        @NotNull Long categoryId,

        List<MultipartFile> attachments
) {
}
