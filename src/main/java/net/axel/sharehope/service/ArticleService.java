package net.axel.sharehope.service;

import net.axel.sharehope.domain.dtos.article.ArticleRequestDTO;
import net.axel.sharehope.domain.dtos.article.ArticleResponseDTO;

public interface ArticleService {

    ArticleResponseDTO create(ArticleRequestDTO requestDTO);
}
