package com.incidenciasvlc.issueservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.incidenciasvlc.issueservice.model.Issue;

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

    public Flux<Issue> getAllIssues() {
        return webClient.get()
                .uri("/api/issues")
                .retrieve()
                .bodyToFlux(Issue.class);
    }

    public Mono<Issue> getIssueById(String id) {
        return webClient.get()
                .uri("/api/issues/{id}", id)
                .retrieve()
                .bodyToMono(Issue.class);
    }

    public Mono<Issue> createIssue(Issue issue) {
        return webClient.post()
                .uri("/api/issues")
                .bodyValue(issue)
                .retrieve()
                .bodyToMono(Issue.class);
    }
}
