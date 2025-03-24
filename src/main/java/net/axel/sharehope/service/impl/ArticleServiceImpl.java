package net.axel.sharehope.service.impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.article.ArticleResponseDTO;
import net.axel.sharehope.domain.dtos.article.CreateArticleDTO;
import net.axel.sharehope.domain.dtos.article.UpdateArticleDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.entities.Article;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.ArticleMapper;
import net.axel.sharehope.repository.ArticleRepository;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.security.repository.AppUserRepository;
import net.axel.sharehope.service.ArticleService;
import net.axel.sharehope.service.AttachmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repository;
    private final ArticleMapper mapper;
    private final AppUserRepository authorRepository;
    private final AttachmentService attachmentService;

    @Override
    public ArticleResponseDTO create(CreateArticleDTO requestDTO) {
        AppUser author = getAuthor(requestDTO.authorId());

        Article article = Article.createArticle(requestDTO.title(), requestDTO.description(), requestDTO.content(), author);

//        TODO: set attachments if exists 🖇️🧾
        Article savedArticle = repository.save(article);

        if (requestDTO.attachments() != null && !requestDTO.attachments().isEmpty()) {
            List<String> attachments = requestDTO.attachments().stream()
                    .map(att -> {
                        AttachmentRequestDTO attachmentDTO = new AttachmentRequestDTO(
                                att,
                                "Article",
                                savedArticle.getId(),
                                "articles"
                        );
                        return attachmentService.createAttachment(attachmentDTO).filePath();
                    }).toList();
            savedArticle.setAttachments(attachments);
        }

        return mapper.toResponse(savedArticle);
    }

    @Override
    public Page<ArticleResponseDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = repository.findAllByOrderByCreatedAtDesc(pageable);

        List<ArticleResponseDTO> articleDtos = articles.stream()
                .map(article -> {
                    List<String> attachments = attachmentService.findAttachmentUrls("Article", article.getId());
                    article.setAttachments(attachments);
                    return mapper.toResponse(article);
                })
                .collect(Collectors.toList());
        
        return new PageImpl<>(articleDtos, pageable, articles.getTotalElements());
    }

    @Override
    public ArticleResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(article -> {
                    List<String> attachments = attachmentService.findAttachmentUrls("Article", article.getId());
                    article.setAttachments(attachments);
                    return mapper.toResponse(article);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Article", id));
    }

    @Override
    public ArticleResponseDTO update(Long id, UpdateArticleDTO updateDTO) {
        Article existingArticle = getArticleById(id);
        existingArticle.updateArticle(updateDTO);

//        TODO: update the attachments.

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
