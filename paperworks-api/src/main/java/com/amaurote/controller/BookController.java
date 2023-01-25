package com.amaurote.controller;

import com.amaurote.catalogue.service.BookService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public record BookController(BookService service) {



}
