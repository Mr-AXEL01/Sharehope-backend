package net.axel.sharehope.service.impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.article.ArticleProjectionDTO;
import net.axel.sharehope.domain.dtos.article.ArticleResponseDTO;
import net.axel.sharehope.domain.dtos.article.CreateArticleDTO;
import net.axel.sharehope.domain.dtos.article.UpdateArticleDTO;
import net.axel.sharehope.domain.entities.Article;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.ArticleMapper;
import net.axel.sharehope.repository.ArticleRepository;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.security.repository.AppUserRepository;
import net.axel.sharehope.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ArticleResponseDTO create(CreateArticleDTO requestDTO) {
        AppUser author = getAuthor(requestDTO.authorId());

        Article article = Article.createArticle(requestDTO.title(), requestDTO.description(), requestDTO.content(), author);

//        TODO: set attachments if exists 🖇️🧾

        Article savedArticle = repository.save(article);

        return mapper.toResponse(savedArticle);
    }

    @Override
    public Page<ArticleProjectionDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Override
    public ArticleProjectionDTO findById(Long id) {
        return repository.findArticleById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", id));
    }

    @Override
    public ArticleResponseDTO update(Long id, UpdateArticleDTO updateDTO) {
        Article existingArticle = getArticleById(id);
        existingArticle.updateArticle(updateDTO);
        return mapper.toResponse(existingArticle);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No article to delete with the ID: " + id);
        }
        repository.deleteById(id);
    }

    private Article getArticleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", id));
    }

    private AppUser getAuthor(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", authorId));
    }
}
