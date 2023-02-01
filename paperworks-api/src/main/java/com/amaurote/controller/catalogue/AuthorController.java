package com.amaurote.controller.catalogue;

import com.amaurote.catalogue.service.AuthorService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public record AuthorController(AuthorService service) {
}
