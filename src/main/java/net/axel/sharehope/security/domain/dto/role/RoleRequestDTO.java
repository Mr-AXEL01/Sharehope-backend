package net.axel.sharehope.security.domain.dto.role;

import jakarta.validation.constraints.NotBlank;

public record RoleRequestDTO(

        @NotBlank String role
) {
}
