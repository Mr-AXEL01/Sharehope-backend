package net.axel.sharehope.security.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.validation.IsUnique;
import org.springframework.web.multipart.MultipartFile;

public record UserRegisterDTO(

        @IsUnique(entityClass = AppUser.class, fieldName = "email", message = "email already taken.")
        @NotBlank String email,

        @NotBlank String password,

        @NotBlank String phone,

        MultipartFile avatar
) {
}
