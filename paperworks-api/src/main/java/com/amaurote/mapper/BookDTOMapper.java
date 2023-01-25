package com.amaurote.mapper;

import com.amaurote.catalogue.utils.CatUtils;
import com.amaurote.dto.BookDTO;
import com.amaurote.domain.entity.Book;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BookDTOMapper implements Function<Book, BookDTO> {

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
                .authorship(null /* todo */)
                .publisher(null /* todo */)
                .language(book.getLanguage())
                .pageCount(book.getPages())
                .weight(book.getWeight())
                .build();
    }

}
