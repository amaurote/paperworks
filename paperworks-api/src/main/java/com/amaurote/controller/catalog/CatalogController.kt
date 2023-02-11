package com.amaurote.controller.catalog

import com.amaurote.catalog.service.BookService
import com.amaurote.catalog.service.CategoryService
import com.amaurote.catalog.service.LanguageService
import com.amaurote.controller.BaseControllerKT
import com.amaurote.domain.entity.Language
import com.amaurote.dto.BookDTO
import com.amaurote.dto.CategoryDTO
import com.amaurote.dto.ReviewDTO
import com.amaurote.mapper.BookDTOMapper
import com.amaurote.mapper.CategoryDTOMapper
import com.amaurote.mapper.ReviewDTOMapper
import com.amaurote.service.ControllerHelperService
import com.amaurote.social.dto.RatingAggregateResults
import com.amaurote.social.service.RatingService
import com.amaurote.social.service.ReviewService
import org.apache.commons.lang.StringUtils
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/catalog")
class CatalogController(
    private val helperService: ControllerHelperService,
    private val bookService: BookService,
    private val ratingService: RatingService,
    private val reviewService: ReviewService,
    private val languageService: LanguageService,
    private val categoryService: CategoryService,
    private val bookDTOMapper: BookDTOMapper,
    private val reviewDTOMapper: ReviewDTOMapper,
    private val categoryDTOMapper: CategoryDTOMapper
) : BaseControllerKT() {

    @GetMapping(value = ["/{catalogId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getBookByCatalogId(@PathVariable(name = "catalogId", required = true) idStr: String): ResponseEntity<BookDTO> {
        val book = helperService.getBookByCatalogIdRequest(idStr)
        return ResponseEntity<BookDTO>(bookDTOMapper.apply(book), HttpStatus.OK)
    }

    @GetMapping(value = ["/search"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun search(@RequestParam(required = false) term: String): ResponseEntity<List<BookDTO>> {
        if (StringUtils.isBlank(term))
            return ok(emptyList())

        val books = bookService.searchBookByTitle(term)
        return ok(books.stream().map(bookDTOMapper).collect(Collectors.toList()))
    }

    @GetMapping(value = ["/{catalogId}/rating"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getBookRating(@PathVariable(name = "catalogId") idStr: String): ResponseEntity<RatingAggregateResults> {
        val book = helperService.getBookByCatalogIdRequest(idStr)
        return ok(ratingService.getBookRating(book))
    }

    // todo pagination
    @GetMapping(value = ["/{catalogId}/reviews"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getBookReviews(@PathVariable(name = "catalogId") idStr: String): ResponseEntity<List<ReviewDTO>> {
        val book = helperService.getBookByCatalogIdRequest(idStr)
        return ok(reviewService.getBookReviews(book).stream().map(reviewDTOMapper).collect(Collectors.toList()))
    }

    @GetMapping(value = ["/languages"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllLanguages(): ResponseEntity<List<Language>> = ok(languageService.allLanguages)

    @GetMapping(value = ["/categories/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<CategoryDTO> =
        ok(categoryDTOMapper.apply(categoryService.getCategoryById(id)))

    @GetMapping(value = ["/categories/{id}/path-map"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCategoryPathMap(@PathVariable id: Long): ResponseEntity<Map<Long, String>> =
        ok(categoryService.generateCategoryPathMap(id))

    @GetMapping(value = ["/categories/tree"], produces = [MediaType.TEXT_PLAIN_VALUE])
    fun getCategoryTree(): ResponseEntity<String> {
        // todo extend settings
        return ok(categoryService.generateTree())
    }

}