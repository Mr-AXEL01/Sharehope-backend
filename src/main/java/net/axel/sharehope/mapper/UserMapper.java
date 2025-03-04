package net.axel.sharehope.mapper;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.article.ArticleEmbeddedDTO;
import net.axel.sharehope.domain.entities.Article;
import net.axel.sharehope.security.domain.dto.role.RoleResponseDTO;
import net.axel.sharehope.security.domain.dto.user.UserResponseDTO;
import net.axel.sharehope.security.domain.entity.AppRole;
import net.axel.sharehope.security.domain.entity.AppUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserResponseDTO mapToResponseDTO(AppUser appUser) {
        return new UserResponseDTO(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getEmail(),
                appUser.getPhone(),
                mapRoles(appUser.getRoles()),
                mapArticle(appUser.getArticles()),
                appUser.getAvatar()
        );
    }

    private Set<RoleResponseDTO> mapRoles(Set<AppRole> roles) {
        return roles.stream()
                .map(role -> new RoleResponseDTO(role.getRole()))
                .collect(Collectors.toSet());
    }

    private List<ArticleEmbeddedDTO> mapArticle(List<Article> articles) {
        return articles.stream()
                .map(article -> new ArticleEmbeddedDTO(
                        article.getId(),
                        article.getTitle(),
                        article.getDescription(),
                        article.getContent(),
                        article.getCreatedAt())
                )
                .collect(Collectors.toList());
    }
}
