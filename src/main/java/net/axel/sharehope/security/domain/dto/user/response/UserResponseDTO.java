package net.axel.sharehope.security.domain.dto.user;

import net.axel.sharehope.domain.dtos.article.ArticleEmbeddedDTO;
import net.axel.sharehope.security.domain.dto.role.RoleResponseDTO;

import java.util.List;
import java.util.Set;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String phone,
        Set<RoleResponseDTO> roles,
        List<ArticleEmbeddedDTO> articles,
        String avatar
) {
}
