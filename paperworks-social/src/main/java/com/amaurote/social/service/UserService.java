package com.amaurote.social.service;

import com.amaurote.domain.entity.User;
import com.amaurote.social.exception.SocialServiceException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface UserService {

    User getUserByUsername(String username) throws SocialServiceException;

    void registerNewUser(UserRegistrationRequestDTO dto);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class UserRegistrationRequestDTO {
        @NotBlank
        private String username;
        @Email
        private String email;
        @NotBlank
        private String password;
    }

}
