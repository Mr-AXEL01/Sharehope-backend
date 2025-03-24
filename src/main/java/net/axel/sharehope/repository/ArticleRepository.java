package net.axel.sharehope.repository;

import net.axel.sharehope.domain.dtos.article.ArticleProjectionDTO;
import net.axel.sharehope.domain.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Optional<ArticleProjectionDTO> findArticleById(Long id);
}