package com.amaurote.catalogue.service;

import com.amaurote.domain.entity.Book;

import java.util.List;

public interface BookService {

    Book getBookByCatalogueNumber(long catId);

    List<Book> searchBookByTitle(String term);

}
