package com.amaurote.service;

import com.amaurote.domain.entity.Role;
import com.amaurote.dto.MyUserDetails;
import com.amaurote.social.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public record MyUserDetailsService(UserRepository userRepository) implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return MyUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .active(user.isActive())
                .enabled(user.isEnabled())
                .authorities(mapRolesToAuthorities(user.getRoles()))
                .build();
    }

    private Set<GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }
}
