package com.amaurote.controller.catalogue;


import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.catalogue.service.BookService;
import com.amaurote.catalogue.service.CategoryService;
import com.amaurote.catalogue.utils.CatUtils;
import com.amaurote.controller.BaseController;
import com.amaurote.mapper.CategoryDTOMapper;
import jakarta.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cat/categories")
public record CategoryController(CategoryService categoryService,
                                 BookService bookService,
                                 CategoryDTOMapper categoryDTOMapper) implements BaseController {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCategoryById(@PathVariable long id) throws CatalogueException {
        return ok(categoryDTOMapper.apply(categoryService.getCategoryById(id)));
    }

    @GetMapping(value = "/path-map/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCategoryPathMap(@PathVariable long id) throws CatalogueException {
        return ok(categoryService.generateCategoryPathMap(id));
    }

    @GetMapping(value = "/tree", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getCategoryTree() {
        // todo extend settings
        return ok(categoryService.generateTree());
    }

    @PutMapping(value = "/assign/{catId}")
    public ResponseEntity<?> assignCategory(
            @PathVariable String catId,
            @RequestParam(name = "category") long categoryId,
            @RequestParam(name = "isMain", required = false, defaultValue = "false") boolean isMain)
            throws CatalogueException {

        var catalogueId = CatUtils.stringToCatalogueNumber9(catId);
        if (catalogueId == null)
            return badRequest();

        var book = bookService.getBookByCatalogueNumber(catalogueId);
        if (book == null)
            return notFound();

        categoryService.assign(book, categoryId, isMain);
        return ok();
    }

    @PostMapping(value = "/unassign/{catId}")
    public ResponseEntity<?> unassignCategory(
            @PathVariable String catId,
            @RequestParam(name = "category") long categoryId) throws CatalogueException {

        var catalogueId = CatUtils.stringToCatalogueNumber9(catId);
        if (catalogueId == null)
            return badRequest();

        var book = bookService.getBookByCatalogueNumber(catalogueId);
        if (book == null)
            return notFound();

        categoryService.unassign(book, categoryId);
        return ok();
    }

    @PostMapping(value = "/unassign-all/{catId}")
    public ResponseEntity<?> unassignAllCategories(@PathVariable String catId) throws CatalogueException {
        var catalogueId = CatUtils.stringToCatalogueNumber9(catId);
        if (catalogueId == null)
            return badRequest();

        var book = bookService.getBookByCatalogueNumber(catalogueId);
        if (book == null)
            return notFound();

        categoryService.unassignAll(book);
        return ok();
    }

    @PostMapping
    public ResponseEntity<?> createSingleCategory(
            @RequestBody @Valid CategoryService.CategoryCreateRequestDTO dto) throws CatalogueException {
        categoryService.createSingleCategory(dto);
        return ok();
    }

    @PutMapping(value = "/build-path")
    public ResponseEntity<?> buildCategoryPath(@RequestBody String path) throws CatalogueException {
        if (StringUtils.isBlank(path))
            return badRequest();

        if (!path.matches("[a-z0-9_.]+"))
            return badRequest();

        categoryService.buildCategoryPath(path, null); // todo implement an option to change parent category
        return ok();
    }

}
