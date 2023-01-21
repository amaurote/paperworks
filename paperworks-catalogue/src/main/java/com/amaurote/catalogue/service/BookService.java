package com.amaurote.catalogue.service;

import com.amaurote.catalogue.domain.entity.Book;
import com.amaurote.catalogue.dto.BookDTO;
import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.catalogue.repository.BookRepository;
import com.amaurote.catalogue.utils.CatUtils;
import org.springframework.stereotype.Service;

@Service
public record BookService(BookRepository repository) {

    public Book getBookByCatalogueNumber(long catId) {
        return repository.findOneByCatalogueId(catId).orElse(null);
    }

    public BookDTO bookToDTO(Book book) throws CatalogueException {
        return BookDTO.builder()
                .name(book.getName())
                .catalogueId(book.getCatalogueId())
                .catalogueIdPretty(CatUtils.prettifyCatalogueNumber9(book.getCatalogueId()))
                .build();
    }
}
