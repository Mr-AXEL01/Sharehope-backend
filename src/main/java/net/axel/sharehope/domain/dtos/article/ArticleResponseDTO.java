package net.axel.sharehope.domain.dtos.article;

import java.time.Instant;

/**
 * Projection for {@link net.axel.sharehope.domain.entities.Article}
 */
public interface ArticleResponseDTO {
    Long getId();

    String getTitle();

    String getDescription();

    String getContent();

    Instant getCreatedAt();

    AppUserNestedDTO getAuthor();

    /**
     * Projection for {@link net.axel.sharehope.security.domain.entity.AppUser}
     */
    interface AppUserNestedDTO {
        Long getId();

        String getUsername();

        String getEmail();

        String getPassword();

        String getPhone();
    }
}