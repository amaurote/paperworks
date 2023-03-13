package com.amaurote.controller.customer

import com.amaurote.controller.BaseControllerKT
import com.amaurote.social.service.UserService
import com.amaurote.social.service.UserService.UserRegistrationRequestDTO
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) : BaseControllerKT() {

    @PostMapping(value = ["/register"])
    fun registerUser(@RequestBody dto: @Valid UserRegistrationRequestDTO): ResponseEntity<Void> {
        userService.registerNewUser(dto)
        return ok()
    }

}