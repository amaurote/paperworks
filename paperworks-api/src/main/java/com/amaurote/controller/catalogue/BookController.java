package com.amaurote.controller.catalogue;

import com.amaurote.catalogue.service.BookService;
import com.amaurote.mapper.BookDTOMapper;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cat")
public record BookController(BookService service, BookDTOMapper bookDTOMapper) {

    @GetMapping(value = "/{catId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookByCatalogueId(@PathVariable @NotNull String catId) {
        var idStr = catId.replaceAll("[^0-9]", "");

        if (StringUtils.isBlank(catId))
            return ResponseEntity.badRequest().build();

        var book = service.getBookByCatalogueNumber(Long.parseLong(idStr));
        return ResponseEntity.ok(bookDTOMapper.apply(book));
    }

}
