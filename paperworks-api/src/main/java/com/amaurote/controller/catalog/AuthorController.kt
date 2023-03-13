package com.amaurote.controller.catalog

import com.amaurote.catalog.service.AuthorService
import com.amaurote.controller.BaseControllerKT
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/catalog")
class AuthorController(private val authorService: AuthorService) : BaseControllerKT() {
}