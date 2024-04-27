package com.incidenciasvlc.issueservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.incidenciasvlc.issueservice.model.Comment;
import com.incidenciasvlc.issueservice.service.CommentService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Mono<ResponseEntity<Comment>> createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment)
                .map(createdComment -> ResponseEntity.status(HttpStatus.CREATED).body(createdComment))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
