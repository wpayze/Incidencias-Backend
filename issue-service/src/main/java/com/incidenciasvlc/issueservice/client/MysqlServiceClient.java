package com.incidenciasvlc.issueservice.client;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.incidenciasvlc.issueservice.model.Category;
import com.incidenciasvlc.issueservice.model.Issue;
import com.incidenciasvlc.issueservice.model.Status;
import com.incidenciasvlc.issueservice.model.User;

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

    public Flux<User> getUsersByIds(Set<Integer> userIds) {
        List<Integer> userIdsList = new ArrayList<>(userIds);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/api/users/byIds")
                .queryParam("ids", userIdsList.toArray());

        return webClient.get()
                .uri(builder.toUriString())
                .retrieve()
                .bodyToFlux(User.class);
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

    public Flux<Issue> getIssuesByUserId(String id) {
        return webClient.get()
                .uri("/api/issues/user/{id}", id)
                .retrieve()
                .bodyToFlux(Issue.class);
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

    public Mono<Status> updateStatus(String id, Status status) {
        return webClient.put()
                .uri("/api/statuses/{id}", id)
                .bodyValue(status)
                .retrieve()
                .bodyToMono(Status.class);
    }

    public Flux<Category> getAllCategories() {
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

    public Mono<Category> updateCategory(String id, Category category) {
        return webClient.put()
                .uri("/api/categories/{id}", id)
                .bodyValue(category)
                .retrieve()
                .bodyToMono(Category.class);
    }

}
