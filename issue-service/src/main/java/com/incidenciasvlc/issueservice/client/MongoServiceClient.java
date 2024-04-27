package com.incidenciasvlc.issueservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.incidenciasvlc.issueservice.model.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MongoServiceClient {

    private final WebClient webClient;

    public MongoServiceClient(@Value("${mongo-service.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<Comment> getCommentsByIssueId(String issueId) {
        return webClient.get()
                .uri("/comments/issue/{id}", issueId)
                .retrieve()
                .bodyToFlux(Comment.class);
    }

    public Mono<Comment> createComment(Comment comment) {
        return webClient.post()
                .uri("/comments")
                .body(BodyInserters.fromValue(comment))
                .retrieve()
                .bodyToMono(Comment.class);
    }
}
