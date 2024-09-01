package com.incidenciasvlc.issueservice.service;

import org.springframework.stereotype.Service;
import com.incidenciasvlc.issueservice.client.MongoServiceClient;
import com.incidenciasvlc.issueservice.model.Comment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentService {

    private final MongoServiceClient mongoServiceClient;

    public CommentService(MongoServiceClient mongoServiceClient) {
        this.mongoServiceClient = mongoServiceClient;
    }

    public Mono<Comment> createComment(Comment comment) {
        return mongoServiceClient.createComment(comment);
    }

    public Flux<Comment> getCommentsByUserId(String userId) {
        return mongoServiceClient.getCommentsByUserId(userId);
    }
}