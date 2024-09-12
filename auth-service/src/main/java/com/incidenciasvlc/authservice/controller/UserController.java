package com.incidenciasvlc.authservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.incidenciasvlc.authservice.model.User;
import com.incidenciasvlc.authservice.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(user))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<?>> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);

        return userService.updateUser(user)
                .<ResponseEntity<?>>map(savedUser -> ResponseEntity.ok().body(savedUser))
                .onErrorResume(e -> Mono.just((ResponseEntity<?>) ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage())))
                .switchIfEmpty(Mono.just((ResponseEntity<?>) ResponseEntity.badRequest().build()));
    }
}
