package net.axel.sharehope.service.impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.article.ArticleRequestDTO;
import net.axel.sharehope.domain.dtos.article.ArticleResponseDTO;
import net.axel.sharehope.domain.entities.Article;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.ArticleMapper;
import net.axel.sharehope.repository.ArticleRepository;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.security.repository.AppUserRepository;
import net.axel.sharehope.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repository;
    private final ArticleMapper mapper;
    private final AppUserRepository authorRepository;

    @Override
    public ArticleResponseDTO create(ArticleRequestDTO requestDTO) {
        AppUser author = getAuthor(requestDTO.authorId());

        Article article = Article.createArticle(requestDTO.title(), requestDTO.description(), requestDTO.content(), author);

//        TODO: set attachments if exists 🖇️🧾

        Article savedArticle = repository.save(article);

        return mapper.toResponse(savedArticle);
    }

    private AppUser getAuthor(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", authorId));
    }
}
