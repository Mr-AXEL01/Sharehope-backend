package net.axel.sharehope.service;

import net.axel.sharehope.domain.dtos.article.ArticleProjectionDTO;
import net.axel.sharehope.domain.dtos.article.CreateArticleDTO;
import net.axel.sharehope.domain.dtos.article.ArticleResponseDTO;
import net.axel.sharehope.domain.dtos.article.UpdateArticleDTO;
import org.springframework.data.domain.Page;

public interface ArticleService {

    ArticleResponseDTO create(CreateArticleDTO requestDTO);

    Page<ArticleResponseDTO> findAll(int page, int size);

    ArticleResponseDTO findById(Long id);

    ArticleResponseDTO update(Long id, UpdateArticleDTO requestDTO);

    void delete(Long id);
}
