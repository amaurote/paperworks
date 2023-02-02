package com.amaurote.controller.catalogue;

import com.amaurote.catalogue.service.BookService;
import com.amaurote.catalogue.service.LanguageService;
import com.amaurote.controller.BaseController;
import com.amaurote.domain.entity.Language;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cat")
public record CatalogueController(LanguageService languageService,
                                  BookService bookService) implements BaseController {

    @GetMapping(value = "/languages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Language>> getAllLanguages() {
        return ok(languageService.getAllLanguages());
    }

}
