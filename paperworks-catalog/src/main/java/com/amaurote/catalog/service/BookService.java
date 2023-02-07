package com.amaurote.catalog.service;

import com.amaurote.domain.entity.Book;

import java.util.List;

public interface BookService {

    Book getBookByCatalogNumber(long catId);

    List<Book> searchBookByTitle(String term);

}
