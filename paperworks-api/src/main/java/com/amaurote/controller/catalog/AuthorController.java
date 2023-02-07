package com.amaurote.controller.catalog;

import com.amaurote.catalog.service.AuthorService;
import com.amaurote.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public record AuthorController(AuthorService service) implements BaseController {
}
