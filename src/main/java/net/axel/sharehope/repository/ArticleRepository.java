package net.axel.sharehope.repository;

import net.axel.sharehope.domain.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}