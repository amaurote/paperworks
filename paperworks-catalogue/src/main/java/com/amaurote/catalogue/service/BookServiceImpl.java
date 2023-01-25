package com.amaurote.catalogue.service;

import com.amaurote.domain.entity.Book;
import com.amaurote.catalogue.repository.BookRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public record BookServiceImpl(BookRepository repository) implements BookService {

    public Book getBookByCatalogueNumber(long catId) {
        return repository.findOneByCatalogueId(catId).orElse(null);
    }

    public List<Book> searchBookByTitle(String term) {
        if (StringUtils.isNotBlank(term) && term.length() > 2) {
            return repository.findByTitleContainsIgnoreCase(term);
        }
        return Collections.emptyList();
    }
}
