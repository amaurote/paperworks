package com.amaurote.controller.catalogue;


import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.catalogue.service.CategoryService;
import com.amaurote.controller.BaseController;
import com.amaurote.mapper.CategoryDTOMapper;
import jakarta.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cat/categories")
public record CategoryController(CategoryService service,
                                 CategoryDTOMapper mapper) implements BaseController {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCategoryById(@PathVariable long id) throws CatalogueException {
        return ok(mapper.apply(service.getCategoryById(id)));
    }

    @GetMapping(value = "/path-map/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCategoryPathMap(@PathVariable long id) throws CatalogueException {
        return ok(service.generateCategoryPathMap(id));
    }

    @GetMapping(value = "/tree", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getCategoryTree() {
        // todo extend settings
        return ok(service.generateTree());
    }

    @PostMapping
    public ResponseEntity<?> createSingleCategory(@RequestBody @Valid CategoryService.CategoryCreateRequestDTO dto) throws CatalogueException {
        service.createSingleCategory(dto);
        return ok();
    }

    @PutMapping(value = "/build-path")
    public ResponseEntity<?> buildCategoryPath(@RequestBody String path) throws CatalogueException {
        if (StringUtils.isBlank(path))
            return badRequest();

        if (!path.matches("[a-z0-9_.]+"))
            return badRequest();

        service.buildCategoryPath(path, null); // todo implement an option to change parent category
        return ok();
    }

}
