package com.incidenciasvlc.issueservice.service;

import org.springframework.stereotype.Service;

import com.incidenciasvlc.issueservice.client.MysqlServiceClient;
import com.incidenciasvlc.issueservice.model.Category;
import com.incidenciasvlc.issueservice.model.Status;

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

    public Mono<Category> updateCategory(String id, Category category) {
        return mysqlServiceClient.updateCategory(id, category)
                .onErrorResume(e -> {
                    return Mono.error(e);
                });
    }
}
