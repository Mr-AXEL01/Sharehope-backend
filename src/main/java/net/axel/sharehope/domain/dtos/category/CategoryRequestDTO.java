package net.axel.sharehope.domain.dtos.category;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record CategoryRequestDTO(

        @NotBlank String categoryName,

        @NotBlank String description,

        MultipartFile icon
) {
}
