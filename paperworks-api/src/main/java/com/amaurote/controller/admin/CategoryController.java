package com.amaurote.controller.admin;


import com.amaurote.catalogue.exception.CatalogueException;
import com.amaurote.catalogue.service.BookService;
import com.amaurote.catalogue.service.CategoryService;
import com.amaurote.catalogue.utils.CatUtils;
import com.amaurote.controller.BaseController;
import com.amaurote.mapper.CategoryDTOMapper;
import jakarta.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
public record CategoryController(CategoryService categoryService,
                                 BookService bookService,
                                 CategoryDTOMapper categoryDTOMapper) implements BaseController {

    @PutMapping(value = "/assign/{catalogueId}")
    public ResponseEntity<?> assignCategory(
            @PathVariable(name = "catalogueId") String idStr,
            @RequestParam(name = "category") long categoryId,
            @RequestParam(name = "isMain", required = false, defaultValue = "false") boolean isMain)
            throws CatalogueException {

        var catalogueId = CatUtils.stringToCatalogueNumber9(idStr);
        if (catalogueId == null)
            return badRequest();

        var book = bookService.getBookByCatalogueNumber(catalogueId);
        if (book == null)
            return notFound();

        categoryService.assign(book, categoryId, isMain);
        return ok();
    }

    @PostMapping(value = "/unassign/{catalogueId}")
    public ResponseEntity<?> unassignCategory(
            @PathVariable(name = "catalogueId") String idStr,
            @RequestParam(name = "category") long categoryId) throws CatalogueException {

        var catalogueId = CatUtils.stringToCatalogueNumber9(idStr);
        if (catalogueId == null)
            return badRequest();

        var book = bookService.getBookByCatalogueNumber(catalogueId);
        if (book == null)
            return notFound();

        categoryService.unassign(book, categoryId);
        return ok();
    }

    @PostMapping(value = "/unassign-all/{catalogueId}")
    public ResponseEntity<?> unassignAllCategories(@PathVariable(name = "catalogueId") String idStr) throws CatalogueException {
        var catalogueId = CatUtils.stringToCatalogueNumber9(idStr);
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

    public ResponseEntity<?> getCategoryPathMap() {
        // todo
        return null;
    }

}
