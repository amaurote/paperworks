package com.amaurote.controller.catalogue;

import com.amaurote.catalogue.service.BookService;
import com.amaurote.catalogue.utils.CatUtils;
import com.amaurote.controller.BaseController;
import com.amaurote.dto.BookDTO;
import com.amaurote.mapper.BookDTOMapper;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cat")
public record BookController(BookService service,
                             BookDTOMapper bookDTOMapper) implements BaseController {

    @GetMapping(value = "/{catId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookByCatalogueId(@PathVariable @NotNull String catId) {
        var catalogueId = CatUtils.stringToCatalogueNumber9(catId);

        if(catalogueId == null)
            return badRequest();

        var book = service.getBookByCatalogueNumber(catalogueId);
        return ok(bookDTOMapper.apply(book));
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> search(@RequestParam(required = false) String term) {
        if (StringUtils.isBlank(term))
            return ok(Collections.emptyList());

        var books = service.searchBookByTitle(term);
        return ok(books.stream().map(bookDTOMapper).collect(Collectors.toList()));
    }

}
