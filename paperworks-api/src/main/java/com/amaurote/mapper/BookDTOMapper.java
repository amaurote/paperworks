package com.amaurote.mapper;

import com.amaurote.catalogue.utils.CatUtils;
import com.amaurote.dto.BookDTO;
import com.amaurote.domain.entity.Book;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public record BookDTOMapper(
        AuthorDTOMapper authorDTOMapper,
        PublisherDTOMapper publisherDTOMapper)
        implements Function<Book, BookDTO> {

    @Override
    public BookDTO apply(Book book) {
        if (book == null)
            return null;

        return BookDTO.builder()
                .catalogueId(book.getCatalogueId())
                .catalogueIdPretty(CatUtils.prettifyCatalogueNumber9(book.getCatalogueId()))
                .isbn10(book.getIsbn10())
                .isbn13(book.getIsbn13())
                .title(book.getTitle())
                .overview(book.getOverview())
                .yearPublished(book.getYearPublished())
                .authorship(book.getAuthorship()
                        .stream()
                        .map(authorDTOMapper)
                        .collect(Collectors.toList()))
                .publisher(publisherDTOMapper.apply(book.getPublisher()))
                .language(book.getLanguage())
                .pageCount(book.getPages())
                .weight(book.getWeight())
                .build();
    }

}
