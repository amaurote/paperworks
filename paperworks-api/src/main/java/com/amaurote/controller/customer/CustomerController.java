package com.amaurote.controller.customer;

import com.amaurote.catalog.service.BookService;
import com.amaurote.catalog.utils.CatalogUtils;
import com.amaurote.controller.BaseController;
import com.amaurote.mapper.ReviewDTOMapper;
import com.amaurote.social.exception.SocialServiceException;
import com.amaurote.social.service.RatingService;
import com.amaurote.social.service.ReviewService;
import com.amaurote.social.service.UserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/customer")
public record CustomerController(BookService bookService,
                                 UserService userService,
                                 RatingService ratingService,
                                 ReviewService reviewService,
                                 ReviewDTOMapper reviewDTOMapper) implements BaseController {

    @GetMapping(value = "/rating")
    public ResponseEntity<Integer> getUserBookRating(
            @RequestParam(name = "book") @NotEmpty String catId,
            Principal principal) throws SocialServiceException {

        var reviewer = userService.getUserByUsername(principal.getName());

        var catalogId = CatalogUtils.stringToCatalogNumber9(catId);
        if (catalogId == null)
            return badRequest();

        var book = bookService.getBookByCatalogNumber(catalogId);
        if (book == null)
            return notFound();

        return ok(ratingService.getUserBookRating(book, reviewer));
    }

    @PutMapping(value = "/rating")
    public ResponseEntity<Void> rateOrUpdate(
            @RequestParam(name = "book") @NotEmpty String catId,
            @RequestParam(name = "value") @NotNull @Min(0) @Max(5) Integer value,
            Principal principal) throws SocialServiceException {

        var reviewer = userService.getUserByUsername(principal.getName());

        var catalogId = CatalogUtils.stringToCatalogNumber9(catId);
        if (catalogId == null)
            return badRequest();

        var book = bookService.getBookByCatalogNumber(catalogId);
        if (book == null)
            return notFound();

        if (value < 1)
            ratingService.deleteRating(book, reviewer);
        else
            ratingService.rateOrUpdate(book, reviewer, value);

        return ok();
    }

    @DeleteMapping(value = "/rating")
    public ResponseEntity<Void> deleteRating(
            @RequestParam(name = "book") @NotEmpty String catId,
            Principal principal) throws SocialServiceException {

        var reviewer = userService.getUserByUsername(principal.getName());

        var catalogId = CatalogUtils.stringToCatalogNumber9(catId);
        if (catalogId == null)
            return badRequest();

        var book = bookService.getBookByCatalogNumber(catalogId);
        if (book == null)
            return notFound();

        ratingService.deleteRating(book, reviewer);
        return ok();
    }
}
