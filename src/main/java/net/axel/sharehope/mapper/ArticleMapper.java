package net.axel.sharehope.mapper;

import net.axel.sharehope.domain.dtos.article.CreateArticleDTO;
import net.axel.sharehope.domain.dtos.article.ArticleResponseDTO;
import net.axel.sharehope.domain.entities.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper extends BaseMapper<Article, CreateArticleDTO, ArticleResponseDTO> {
}
