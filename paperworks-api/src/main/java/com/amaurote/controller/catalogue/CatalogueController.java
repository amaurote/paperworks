package com.amaurote.controller.catalogue;

import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.catalogue.service.CategoryService;
import com.amaurote.catalogue.service.LanguageService;
import com.amaurote.controller.BaseController;
import com.amaurote.domain.entity.Language;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cat")
public record CatalogueController(LanguageService languageService,
                                  CategoryService categoryService) implements BaseController {

    @GetMapping(value = "/languages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Language>> getAllLanguages() {
        return ok(languageService.getAllLanguages());
    }

//    @GetMapping(value = "/categories/tree", produces = MediaType.TEXT_PLAIN_VALUE)
//    public ResponseEntity<String> getCategoriesTree() {
//
//    }

    @PutMapping(value = "/categories/build-path")
    public ResponseEntity<?> buildCategoryPath(@RequestBody String path) throws CatalogueException {
        if(StringUtils.isBlank(path))
            return badRequest();

        if(!path.matches("[a-z0-9.]+"))
            return badRequest();

        categoryService.buildCategoryPath(path);
        return ok();
    }


}
