package com.amaurote.controller.customer

import com.amaurote.controller.BaseControllerKT
import com.amaurote.dto.ReviewDTO
import com.amaurote.mapper.ReviewDTOMapper
import com.amaurote.service.ControllerHelperService
import com.amaurote.social.service.RatingService
import com.amaurote.social.service.ReviewService
import com.amaurote.social.service.ReviewService.UserReviewRequestDTO
import com.amaurote.social.service.UserService
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/customer")
class CustomerController(
    private val helperService: ControllerHelperService,
    private val userService: UserService,
    private val ratingService: RatingService,
    private val reviewService: ReviewService,
    private val reviewDTOMapper: ReviewDTOMapper
) : BaseControllerKT() {

    @GetMapping(value = ["/rating"])
    fun getUserBookRating(
        @RequestParam(name = "book") catId: @NotEmpty String,
        principal: Principal
    ): ResponseEntity<Int> {
        val reviewer = userService.getUserByUsername(principal.name)
        val book = helperService.getBookByCatalogIdRequest(catId)
        return ok(ratingService.getUserBookRating(book, reviewer))
    }

    @PutMapping(value = ["/rating"])
    fun rateOrUpdate(
        @RequestParam(name = "book") catId: @NotEmpty String,
        @RequestParam(name = "value") value: @NotNull @Min(0) @Max(5) Int,
        principal: Principal
    ): ResponseEntity<Void> {
        val reviewer = userService.getUserByUsername(principal.name)
        val book = helperService.getBookByCatalogIdRequest(catId)

        when {
            (value < 1) -> ratingService.deleteRating(book, reviewer)
            else -> ratingService.rateOrUpdate(book, reviewer, value)
        }

        return ok()
    }

    @DeleteMapping(value = ["/rating"])
    fun deleteRating(
        @RequestParam(name = "book") catId: @NotEmpty String,
        principal: Principal
    ): ResponseEntity<Void> {
        val reviewer = userService.getUserByUsername(principal.name)
        val book = helperService.getBookByCatalogIdRequest(catId)
        ratingService.deleteRating(book, reviewer)
        return ok()
    }

    @GetMapping(value = ["/review"])
    fun getUserBookReview(
        @RequestParam(name = "book") catId: @NotEmpty String,
        principal: Principal
    ): ResponseEntity<ReviewDTO> {
        val reviewer = userService.getUserByUsername(principal.name)
        val book = helperService.getBookByCatalogIdRequest(catId)
        return ok(reviewDTOMapper.apply(reviewService.getUserBookReview(book, reviewer)))
    }

    @PutMapping(value = ["/review"])
    fun reviewOrUpdate(
        @RequestBody dto: UserReviewRequestDTO,
        principal: Principal
    ): ResponseEntity<Void> {
        val reviewer = userService.getUserByUsername(principal.name)
        val book = helperService.getBookByCatalogIdRequest(dto.book)
        reviewService.reviewOrUpdate(book, reviewer, dto.text)
        return ok()
    }

    @DeleteMapping(value = ["/review"])
    fun deleteReview(
        @RequestParam(name = "book") catId: @NotEmpty String,
        principal: Principal
    ): ResponseEntity<Void> {
        val reviewer = userService.getUserByUsername(principal.name)
        val book = helperService.getBookByCatalogIdRequest(catId)
        reviewService.deleteReview(book, reviewer)
        return ok()
    }

    // todo refactor
    // todo consider wrapping rating and review into one dto
}