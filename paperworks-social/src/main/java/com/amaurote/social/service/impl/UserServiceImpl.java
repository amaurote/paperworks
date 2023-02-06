package com.amaurote.social.service.impl;

import com.amaurote.domain.entity.User;
import com.amaurote.social.exception.SocialServiceException;
import com.amaurote.social.repository.RoleRepository;
import com.amaurote.social.repository.UserRepository;
import com.amaurote.social.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public record UserServiceImpl(UserRepository userRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) implements UserService {

    @Override
    public User getUserByUsername(String username) throws SocialServiceException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new SocialServiceException("User \"" + username + "\" does not exist"));
    }

    @Override
    public void registerNewUser(UserRegistrationRequestDTO dto) {
        var userRole = roleRepository.findByNameIgnoreCase("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));

        var user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .active(true)
                .enabled(true)
                .loginByEmail(true)
                .roles(Set.of(userRole))
                .build();

        userRepository.saveAndFlush(user);
    }
}
