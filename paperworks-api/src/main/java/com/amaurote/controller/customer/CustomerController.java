package com.amaurote.controller.customer;

import com.amaurote.controller.BaseController;
import com.amaurote.mapper.ReviewDTOMapper;
import com.amaurote.service.ControllerHelperService;
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
public record CustomerController(ControllerHelperService helperService,
                                 UserService userService,
                                 RatingService ratingService,
                                 ReviewService reviewService,
                                 ReviewDTOMapper reviewDTOMapper) implements BaseController {

    @GetMapping(value = "/rating")
    public ResponseEntity<Integer> getUserBookRating(
            @RequestParam(name = "book") @NotEmpty String catId,
            Principal principal) throws SocialServiceException {
        var reviewer = userService.getUserByUsername(principal.getName());
        var book = helperService.getBookByCatalogIdRequest(catId);

        return ok(ratingService.getUserBookRating(book, reviewer));
    }

    @PutMapping(value = "/rating")
    public ResponseEntity<Void> rateOrUpdate(
            @RequestParam(name = "book") @NotEmpty String catId,
            @RequestParam(name = "value") @NotNull @Min(0) @Max(5) Integer value,
            Principal principal) throws SocialServiceException {
        var reviewer = userService.getUserByUsername(principal.getName());
        var book = helperService.getBookByCatalogIdRequest(catId);

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
        var book = helperService.getBookByCatalogIdRequest(catId);

        ratingService.deleteRating(book, reviewer);
        return ok();
    }

    @GetMapping(value = "/review")
    public ResponseEntity<?> getUserBookReview(
            @RequestParam(name = "book") @NotEmpty String catId,
            Principal principal) throws SocialServiceException {
        var reviewer = userService.getUserByUsername(principal.getName());
        var book = helperService.getBookByCatalogIdRequest(catId);

        return ok(reviewDTOMapper.apply(reviewService.getUserBookReview(book, reviewer)));
    }

    @PutMapping(value = "/review")
    public ResponseEntity<Void> reviewOrUpdate(
            @RequestBody ReviewService.UserReviewRequestDTO dto,
            Principal principal) throws SocialServiceException {
        var reviewer = userService.getUserByUsername(principal.getName());
        var book = helperService.getBookByCatalogIdRequest(dto.getBook());

        reviewService.reviewOrUpdate(book, reviewer, dto.getText());
        return ok();
    }

    @DeleteMapping(value = "/review")
    public ResponseEntity<Void> deleteReview(
            @RequestParam(name = "book") @NotEmpty String catId,
            Principal principal) throws SocialServiceException {
        var reviewer = userService.getUserByUsername(principal.getName());
        var book = helperService.getBookByCatalogIdRequest(catId);

        reviewService.deleteReview(book, reviewer);
        return ok();
    }

    // todo refactor
    // todo consider wrapping rating and review into one dto
}
