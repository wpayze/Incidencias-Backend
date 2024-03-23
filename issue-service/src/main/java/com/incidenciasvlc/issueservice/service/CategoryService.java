package com.incidenciasvlc.issueservice.service;

import org.springframework.stereotype.Service;

import com.incidenciasvlc.issueservice.client.MysqlServiceClient;
import com.incidenciasvlc.issueservice.model.Category;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {

    private final MysqlServiceClient mysqlServiceClient;

    public CategoryService(MysqlServiceClient mysqlServiceClient) {
        this.mysqlServiceClient = mysqlServiceClient;
    }

    public Flux<Category> getAllCategories() {
        return mysqlServiceClient.getAllCategories()
                .onErrorResume(e -> {
                    return Flux.empty();
                });
    }

    public Mono<Category> getCategoryById(String id) {
        return mysqlServiceClient.getCategoryById(id)
                .onErrorResume(e -> {
                    return Mono.empty();
                });
    }

    public Mono<Category> createCategory(Category category) {
        return mysqlServiceClient.createCategory(category)
                .onErrorResume(e -> {
                    return Mono.error(e);
                });
    }
}
