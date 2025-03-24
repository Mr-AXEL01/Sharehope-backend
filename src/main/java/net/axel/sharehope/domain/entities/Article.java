package net.axel.sharehope.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.axel.sharehope.domain.dtos.article.UpdateArticleDTO;
import net.axel.sharehope.security.domain.entity.AppUser;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static net.axel.sharehope.util.UpdateUtils.updateField;

@Entity
@Table(name = "articles")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AppUser author;

    @Transient
    private List<String> attachments = new ArrayList<>();

    public static Article createArticle(String title, String description, String content, AppUser author) {
        Article article = new Article();
        article.title = title;
        article.description = description;
        article.content = content;
        article.createdAt = Instant.now();
        article.author = author;
        return article;
    }

    public void updateArticle(UpdateArticleDTO updateDto) {
        updateField(updateDto.title(), this.title, newValue -> this.title = newValue);
        updateField(updateDto.description(), this.description, newValue -> this.description = newValue);
        updateField(updateDto.content(), this.content, newValue -> this.content = newValue);
    }
}
