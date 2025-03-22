package net.axel.sharehope.security.domain.dto.user.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPasswordUpdateDTO(

        @NotBlank String currentPassword,

        @NotBlank
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String newPassword
) {
}
