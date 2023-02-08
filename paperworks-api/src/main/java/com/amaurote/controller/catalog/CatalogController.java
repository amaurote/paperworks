package com.amaurote.controller.catalog;

import com.amaurote.catalog.exception.CatalogException;
import com.amaurote.catalog.service.BookService;
import com.amaurote.catalog.service.CategoryService;
import com.amaurote.catalog.service.LanguageService;
import com.amaurote.controller.BaseController;
import com.amaurote.domain.entity.Language;
import com.amaurote.dto.BookDTO;
import com.amaurote.dto.ReviewDTO;
import com.amaurote.mapper.BookDTOMapper;
import com.amaurote.mapper.CategoryDTOMapper;
import com.amaurote.mapper.ReviewDTOMapper;
import com.amaurote.service.ControllerHelperService;
import com.amaurote.social.service.RatingService;
import com.amaurote.social.service.ReviewService;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public record CatalogController(ControllerHelperService helperService,
                                BookService bookService,
                                RatingService ratingService,
                                ReviewService reviewService,
                                LanguageService languageService,
                                CategoryService categoryService,
                                BookDTOMapper bookDTOMapper,
                                ReviewDTOMapper reviewDTOMapper,
                                CategoryDTOMapper categoryDTOMapper) implements BaseController {

    @GetMapping(value = "/{catalogId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookByCatalogId(@PathVariable(name = "catalogId") @NotNull String idStr) {
        var book = helperService.getBookByCatalogIdRequest(idStr);
        return ok(bookDTOMapper.apply(book));
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> search(@RequestParam(required = false) String term) {
        if (StringUtils.isBlank(term))
            return ok(Collections.emptyList());

        var books = bookService.searchBookByTitle(term);
        return ok(books.stream().map(bookDTOMapper).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{catalogId}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookRating(@PathVariable(name = "catalogId") String idStr) {
        var book = helperService.getBookByCatalogIdRequest(idStr);
        return ok(ratingService.getBookRating(book));
    }

    // todo pagination
    @GetMapping(value = "/{catalogId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReviewDTO>> getBookReviews(@PathVariable(name = "catalogId") String idStr) {
        var book = helperService.getBookByCatalogIdRequest(idStr);
        return ok(reviewService.getBookReviews(book).stream().map(reviewDTOMapper).collect(Collectors.toList()));
    }

    @GetMapping(value = "/languages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Language>> getAllLanguages() {
        return ok(languageService.getAllLanguages());
    }

    @GetMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCategoryById(@PathVariable long id) throws CatalogException {
        return ok(categoryDTOMapper.apply(categoryService.getCategoryById(id)));
    }

    @GetMapping(value = "/categories/{id}/path-map", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCategoryPathMap(@PathVariable long id) throws CatalogException {
        return ok(categoryService.generateCategoryPathMap(id));
    }

    @GetMapping(value = "/categories/tree", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getCategoryTree() {
        // todo extend settings
        return ok(categoryService.generateTree());
    }
}
