package com.incidenciasvlc.authservice.controller;

import com.incidenciasvlc.authservice.model.User;
import com.incidenciasvlc.authservice.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Mono<ResponseEntity<?>> registerUser(@RequestBody User user) {
        return authService.registerUser(user)
                .<ResponseEntity<?>>map(savedUser -> ResponseEntity.ok().body(savedUser))
                .onErrorResume(e -> Mono.just((ResponseEntity<?>) ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage())))
                .switchIfEmpty(Mono.just((ResponseEntity<?>) ResponseEntity.badRequest().build()));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody User loginRequest) {
        return authService.validateLoginAndGenerateToken(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken() {
        return ResponseEntity.ok().build();
    }

}
