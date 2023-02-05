package com.amaurote.social.service.impl;

import com.amaurote.domain.entity.User;
import com.amaurote.social.exception.SocialServiceException;
import com.amaurote.social.repository.UserRepository;
import com.amaurote.social.service.UserService;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
public record UserServiceImpl(UserRepository userRepository) implements UserService {

    @Override
    public User getUserByUsername(String username) throws SocialServiceException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new SocialServiceException("User \"" + username + "\" does not exist"));
    }

    @Override
    public void registerNewUser(UserRegistrationRequestDTO dto) {
        throw new NotImplementedException();
    }
}
