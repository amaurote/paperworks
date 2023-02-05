package com.amaurote.social.service;

import com.amaurote.domain.entity.User;
import com.amaurote.social.exception.SocialServiceException;
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
        private String username;
        private String password;
        private String email;
    }

}
