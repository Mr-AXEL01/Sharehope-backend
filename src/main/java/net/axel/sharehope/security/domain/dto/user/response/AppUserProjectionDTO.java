package net.axel.sharehope.security.domain.dto.user.response;

import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * Projection for {@link net.axel.sharehope.security.domain.entity.AppUser}
 */
public interface AppUserProjectionDTO {
    Long getId();

    String getUsername();

    String getEmail();

    String getPhone();

    Set<AppRoleNestedDTO> getRoles();

    List<ArticleNestedDTO> getArticles();

    /**
     * Projection for {@link net.axel.sharehope.security.domain.entity.AppRole}
     */
    interface AppRoleNestedDTO {
        String getRole();
    }

    /**
     * Projection for {@link net.axel.sharehope.domain.entities.Article}
     */
    interface ArticleNestedDTO {
        Long getId();

        String getTitle();

        String getDescription();

        String getContent();

        Instant getCreatedAt();
    }
}