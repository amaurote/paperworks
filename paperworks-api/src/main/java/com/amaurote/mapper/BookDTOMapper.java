package com.amaurote.mapper;

import com.amaurote.catalog.utils.CatalogUtils;
import com.amaurote.domain.entity.Author;
import com.amaurote.domain.entity.BookCategory;
import com.amaurote.dto.BookDTO;
import com.amaurote.domain.entity.Book;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public record BookDTOMapper() implements Function<Book, BookDTO> {

    @Override
    public BookDTO apply(Book book) {
        if (book == null)
            return null;

        return BookDTO.builder()
                .catalogId(book.getCatalogId())
                .catalogIdPretty(CatalogUtils.prettifyCatalogNumber9(book.getCatalogId()))
                .isbn10(book.getIsbn10())
                .isbn13(book.getIsbn13())
                .title(book.getTitle())
                .overview(book.getOverview())
                .yearPublished(book.getYearPublished())
                .authorship(book.getAuthorship()
                        .stream()
                        .collect(Collectors.toMap(Author::getId, Author::getDisplayName)))
                .publisher(Collections.singletonMap(book.getPublisher().getId(), book.getPublisher().getName()))
                .language(book.getLanguage())
                .pageCount(book.getPages())
                .weight(book.getWeight())
                .mainCategory(book.getCategories()
                        .stream().filter(BookCategory::isMainCategory)
                        .map(BookCategory::getCategory)
                        .collect(Collectors.toSet()))
                .otherCategories(book.getCategories()
                        .stream().filter(entry -> !entry.isMainCategory())
                        .map(BookCategory::getCategory)
                        .collect(Collectors.toSet()))
                .build();
    }

}
