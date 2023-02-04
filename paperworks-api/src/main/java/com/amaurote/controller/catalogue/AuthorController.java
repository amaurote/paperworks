package com.amaurote.controller.catalogue;

import com.amaurote.catalogue.service.AuthorService;
import com.amaurote.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalogue")
public record AuthorController(AuthorService service) implements BaseController {
}
