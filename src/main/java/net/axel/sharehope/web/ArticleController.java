package net.axel.sharehope.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.article.ArticleRequestDTO;
import net.axel.sharehope.domain.dtos.article.ArticleResponseDTO;
import net.axel.sharehope.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ArticleController.CONTROLLER_PATH)

@RequiredArgsConstructor
public class ArticleController {

    public final static String CONTROLLER_PATH = "api/v1/articles";

    private final ArticleService service;

    @PostMapping
    public ResponseEntity<ArticleResponseDTO> create(@RequestBody @Valid ArticleRequestDTO requestDto) {
        ArticleResponseDTO article = service.create(requestDto);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }
}
