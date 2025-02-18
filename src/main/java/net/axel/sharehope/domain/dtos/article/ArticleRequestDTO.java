package net.axel.sharehope.domain.dtos.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.validation.Exists;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ArticleRequestDTO(

        @NotBlank String title,

        @NotBlank String description,

        @NotBlank String content,

        @Exists(entityClass = AppUser.class, fieldName = "id", message = "Author does not exists.")
        @NotNull Long authorId,

        List<MultipartFile> attachments
) {
}
