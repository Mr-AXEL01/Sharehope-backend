package net.axel.sharehope.security.domain.dto.user.requests;

public record UserUpdateDTO(
        String username,
        String email,
        String phone
) {
}
