package com.incidenciasvlc.issueservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.incidenciasvlc.issueservice.model.Category;
import com.incidenciasvlc.issueservice.model.Issue;
import com.incidenciasvlc.issueservice.model.Status;

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

    public Flux<Status> getAllStatuses() {
        return webClient.get()
                .uri("/api/statuses")
                .retrieve()
                .bodyToFlux(Status.class);
    }

    public Mono<Status> getStatusById(String id) {
        return webClient.get()
                .uri("/api/statuses/{id}", id)
                .retrieve()
                .bodyToMono(Status.class);
    }

    public Mono<Status> createStatus(Status status) {
        return webClient.post()
                .uri("/api/statuses")
                .bodyValue(status)
                .retrieve()
                .bodyToMono(Status.class);
    }

    public Flux<Category> getAllCategories () {
        return webClient.get()
                .uri("/api/categories")
                .retrieve()
                .bodyToFlux(Category.class);
    }

    public Mono<Category> getCategoryById(String id) {
        return webClient.get()
                .uri("/api/categories/{id}", id)
                .retrieve()
                .bodyToMono(Category.class);
    }
    
    public Mono<Category> createCategory(Category category) {
        return webClient.post()
                .uri("/api/categories")
                .bodyValue(category)
                .retrieve()
                .bodyToMono(Category.class);
    }
}
