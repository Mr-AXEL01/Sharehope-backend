package net.axel.sharehope.security.domain.dto.user.requests;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(

        @NotBlank String username,

        @NotBlank String password
) {
}
