package com.amaurote.controller.catalogue;

import com.amaurote.catalogue.service.BookService;
import com.amaurote.catalogue.utils.CatUtils;
import com.amaurote.controller.BaseController;
import com.amaurote.dto.ReviewDTO;
import com.amaurote.mapper.ReviewDTOMapper;
import com.amaurote.social.service.RatingService;
import com.amaurote.social.service.ReviewService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cat")
public record SocialController(BookService bookService,
                               RatingService ratingService,
                               ReviewService reviewService,
                               ReviewDTOMapper reviewDTOMapper) implements BaseController {

    @GetMapping(value = "/rating/{catId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookRating(@PathVariable String catId) {
        var catalogueId = CatUtils.stringToCatalogueNumber9(catId);
        if (catalogueId == null)
            return badRequest();

        var book = bookService.getBookByCatalogueNumber(catalogueId);

        if (book == null)
            return notFound();

        return ok(ratingService.getBookRating(book));
    }

    @GetMapping(value = "/review/{catId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReviewDTO>> getBookReviews(@PathVariable String catId) {
        var catalogueId = CatUtils.stringToCatalogueNumber9(catId);
        if (catalogueId == null)
            return badRequest();

        var book = bookService.getBookByCatalogueNumber(catalogueId);

        if (book == null)
            return notFound();

        return ok(reviewService.getBookReviews(book).stream().map(reviewDTOMapper).collect(Collectors.toList()));
    }

}
