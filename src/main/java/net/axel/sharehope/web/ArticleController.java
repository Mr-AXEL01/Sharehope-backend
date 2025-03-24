package net.axel.sharehope.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.article.ArticleProjectionDTO;
import net.axel.sharehope.domain.dtos.article.CreateArticleDTO;
import net.axel.sharehope.domain.dtos.article.ArticleResponseDTO;
import net.axel.sharehope.domain.dtos.article.UpdateArticleDTO;
import net.axel.sharehope.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ArticleController.CONTROLLER_PATH)

@RequiredArgsConstructor
public class ArticleController {

    public final static String CONTROLLER_PATH = "api/v1/articles";

    private final ArticleService service;

    @PostMapping
    public ResponseEntity<ArticleResponseDTO> create(@ModelAttribute @Valid CreateArticleDTO requestDto) {
        ArticleResponseDTO article = service.create(requestDto);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ArticleResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ArticleResponseDTO> articles = service.findAll(page, size);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> findById(@PathVariable("id") Long id) {
        ArticleResponseDTO article = service.findById(id);
        return ResponseEntity.ok(article);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateArticleDTO updateDTO
    ) {
        ArticleResponseDTO updatedArticle = service.update(id, updateDTO);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
