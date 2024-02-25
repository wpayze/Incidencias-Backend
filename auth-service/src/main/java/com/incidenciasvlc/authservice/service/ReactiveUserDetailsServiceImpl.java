package com.incidenciasvlc.authservice.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserService userService;

    public ReactiveUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.getUserByEmail(username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException("User not found"))))
                .map(user -> {
                    return org.springframework.security.core.userdetails.User
                            .withUsername(user.getEmail())
                            .password(user.getPassword()) 
                            .authorities("ROLE_USER")
                            .accountExpired(false)
                            .accountLocked(false)
                            .credentialsExpired(false)
                            .disabled(false)
                            .build();
                });
    }
}
