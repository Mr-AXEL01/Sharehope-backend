package net.axel.sharehope.domain.dtos.action;

import jakarta.validation.constraints.NotBlank;

public record ActionStatusDTO(

        @NotBlank String status
) {
}
