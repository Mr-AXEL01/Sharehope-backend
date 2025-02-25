package net.axel.sharehope.domain.dtos.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UpdateArticleDTO(

        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        String title,

        @Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters")
        String description,

        @NotBlank(message = "Content cannot be empty")
        String content,

        List<MultipartFile> attachments
) {
}
