package com.incidenciasvlc.issueservice.service;

import org.springframework.stereotype.Service;

import com.incidenciasvlc.issueservice.client.MysqlServiceClient;
import com.incidenciasvlc.issueservice.model.Issue;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IssueService {

    private final MysqlServiceClient mysqlServiceClient;

    public IssueService(MysqlServiceClient mysqlServiceClient) {
        this.mysqlServiceClient = mysqlServiceClient;
    }

    public Flux<Issue> findAllIssues() {
        return mysqlServiceClient.getAllIssues()
                .onErrorResume(e -> {
                    return Flux.empty();
                });
    }

    public Mono<Issue> findIssueById(String id) {
        return mysqlServiceClient.getIssueById(id)
                .onErrorResume(e -> {
                    return Mono.empty();
                });
    }

    public Mono<Issue> saveIssue(Issue issue) {
        return mysqlServiceClient.createIssue(issue)
                .onErrorResume(e -> {
                    return Mono.error(e);
                });
    }
}
