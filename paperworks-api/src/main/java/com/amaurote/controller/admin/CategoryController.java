package com.amaurote.controller.admin;


import com.amaurote.catalog.exception.CatalogException;
import com.amaurote.catalog.service.CategoryService;
import com.amaurote.controller.BaseController;
import com.amaurote.mapper.CategoryDTOMapper;
import com.amaurote.service.ControllerHelperService;
import jakarta.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
public record CategoryController(ControllerHelperService helperService,
                                 CategoryService categoryService,
                                 CategoryDTOMapper categoryDTOMapper) implements BaseController {

    @PutMapping(value = "/assign/{catalogId}")
    public ResponseEntity<?> assignCategory(
            @PathVariable(name = "catalogId") String idStr,
            @RequestParam(name = "category") long categoryId,
            @RequestParam(name = "isMain", required = false, defaultValue = "false") boolean isMain)
            throws CatalogException {

        var book = helperService.getBookByCatalogIdRequest(idStr);
        categoryService.assign(book, categoryId, isMain);
        return ok();
    }

    @PostMapping(value = "/unassign/{catalogId}")
    public ResponseEntity<?> unassignCategory(
            @PathVariable(name = "catalogId") String idStr,
            @RequestParam(name = "category") long categoryId) throws CatalogException {

        var book = helperService.getBookByCatalogIdRequest(idStr);
        categoryService.unassign(book, categoryId);
        return ok();
    }

    @PostMapping(value = "/unassign-all/{catalogId}")
    public ResponseEntity<?> unassignAllCategories(@PathVariable(name = "catalogId") String idStr) throws CatalogException {

        var book = helperService.getBookByCatalogIdRequest(idStr);
        categoryService.unassignAll(book);
        return ok();
    }

    @PostMapping
    public ResponseEntity<?> createSingleCategory(
            @RequestBody @Valid CategoryService.CategoryCreateRequestDTO dto) throws CatalogException {
        categoryService.createSingleCategory(dto);
        return ok();
    }

    @PutMapping(value = "/build-path")
    public ResponseEntity<?> buildCategoryPath(@RequestBody String path) throws CatalogException {
        if (StringUtils.isBlank(path))
            return badRequest();

        if (!path.matches("[a-z0-9_.]+"))
            return badRequest();

        categoryService.buildCategoryPath(path, null); // todo implement an option to change parent category
        return ok();
    }
}
