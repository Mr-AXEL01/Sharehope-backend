package net.axel.sharehope.security.domain.dto.user;

public record UserUpdateDTO(
        String username,
        String email,
        String phone
) {
}
