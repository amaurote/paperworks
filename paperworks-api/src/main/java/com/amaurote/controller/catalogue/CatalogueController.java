package com.amaurote.controller.catalogue;

import com.amaurote.catalogue.service.LanguageService;
import com.amaurote.controller.BaseController;
import com.amaurote.domain.entity.Language;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cat")
public record CatalogueController(LanguageService languageService) implements BaseController {

    @GetMapping(value = "/languages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Language>> getAllLanguages() {
        return ok(languageService.getAllLanguages());
    }

}
