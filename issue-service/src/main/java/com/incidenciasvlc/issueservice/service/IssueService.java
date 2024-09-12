package com.incidenciasvlc.issueservice.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.incidenciasvlc.issueservice.client.MongoServiceClient;
import com.incidenciasvlc.issueservice.client.MysqlServiceClient;
import com.incidenciasvlc.issueservice.model.Comment;
import com.incidenciasvlc.issueservice.model.Issue;
import com.incidenciasvlc.issueservice.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IssueService {

    private final MysqlServiceClient mysqlServiceClient;
    private final MongoServiceClient mongoServiceClient;

    public IssueService(MysqlServiceClient mysqlServiceClient, MongoServiceClient mongoServiceClient) {
        this.mysqlServiceClient = mysqlServiceClient;
        this.mongoServiceClient = mongoServiceClient;
    }

    public Flux<Issue> findAllIssues() {
        return mysqlServiceClient.getAllIssues()
                .onErrorResume(e -> {
                    return Flux.empty();
                });
    }

    public Flux<Issue> findAllIssuesByUserId(String id) {
        return mysqlServiceClient.getIssuesByUserId(id)
                .onErrorResume(e -> {
                    return Flux.empty();
                });
    }

    public Mono<Issue> findIssueById(String id) {
        return mysqlServiceClient.getIssueById(id)
                .flatMap(issue -> mongoServiceClient.getCommentsByIssueId(id)
                        .collectList()
                        .onErrorResume(error -> {
                            return Mono.just(new ArrayList<Comment>());
                        })
                        .flatMap(comments -> {
                            issue.setComments(comments);

                            if (comments.isEmpty())
                                return Mono.just(issue);

                            Set<Integer> userIds = comments.stream()
                                    .map(Comment::getUserId)
                                    .collect(Collectors.toSet());

                            return fetchUsers(userIds)
                                    .collectList()
                                    .doOnNext(users -> {
                                        Map<Integer, User> userMap = users.stream()
                                                .collect(Collectors.toMap(User::getId, user -> user));
                                        comments.forEach(comment -> comment.setUser(userMap.get(comment.getUserId())));
                                    })
                                    .thenReturn(issue);
                        }))
                .onErrorResume(e -> Mono.empty());
    }

    private Flux<User> fetchUsers(Set<Integer> userIds) {
        return mysqlServiceClient.getUsersByIds(userIds);
    }

    public Mono<Issue> saveIssue(Issue issue) {
        return mysqlServiceClient.createIssue(issue)
                .onErrorResume(e -> {
                    return Mono.error(e);
                });
    }

    public Mono<Issue> updateIssue(String id, Issue issue) {
        return findIssueById(id) 
            .flatMap(existingIssue -> {
                issue.setId(existingIssue.getId()); 
                return mysqlServiceClient.updateIssue(issue);
            })
            .onErrorResume(e -> Mono.error(new Exception("Error al actualizar la incidencia: " + e.getMessage())));
    }
}
