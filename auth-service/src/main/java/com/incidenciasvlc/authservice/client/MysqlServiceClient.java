package com.incidenciasvlc.authservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.incidenciasvlc.authservice.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MysqlServiceClient {

    private final WebClient webClient;

    public MysqlServiceClient(@Value("${mysql-service.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<User> getAllUsers() {
        return webClient.get()
                .uri("/api/users")
                .retrieve()
                .bodyToFlux(User.class);
    }

    public Mono<User> getUserById(String id) {
        return webClient.get()
                .uri("/api/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<User> getUserByEmail(String email) {
        return webClient.get()
                .uri("/api/users/email/{email}", email)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<User> createUser(User user) {
        return webClient.post()
                .uri("/api/users")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<User> updateUser(User user) {
        return webClient.put()
                .uri("/api/users/{id}", user.getId())
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class);
    }
}
