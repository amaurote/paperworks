package com.amaurote.controller.customer;

import com.amaurote.controller.BaseController;
import com.amaurote.social.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController implements BaseController {

    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserService.UserRegistrationRequestDTO dto) {
        userService.registerNewUser(dto);
        return ok();
    }
}
