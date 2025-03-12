package net.axel.sharehope.security.domain.dto.user;

public record UserEmbeddedDTO(
        Long id,
        String username,
        String email,
        String phone,
        String avatar
) {
}
