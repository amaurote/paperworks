package com.amaurote.service;

import com.amaurote.catalog.service.BookService;
import com.amaurote.catalog.utils.CatalogUtils;
import com.amaurote.domain.entity.Book;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public record ControllerHelperService(BookService bookService) {

    public Book getBookByCatalogIdRequest(String catalogIdStr) {
        var catalogId = CatalogUtils.stringToCatalogNumber9(catalogIdStr);
        if (catalogId == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);  // replace with custom

        var book = bookService.getBookByCatalogNumber(catalogId);
        if (book == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); // replace with custom

        return book;
    }

}
