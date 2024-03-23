package com.incidenciasvlc.issueservice.service;

import org.springframework.stereotype.Service;

import com.incidenciasvlc.issueservice.client.MysqlServiceClient;
import com.incidenciasvlc.issueservice.model.Status;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StatusService {

    private final MysqlServiceClient mysqlServiceClient;

    public StatusService(MysqlServiceClient mysqlServiceClient) {
        this.mysqlServiceClient = mysqlServiceClient;
    }

    public Flux<Status> getAllStatuses() {
        return mysqlServiceClient.getAllStatuses()
                .onErrorResume(e -> {
                    return Flux.empty();
                });
    }

    public Mono<Status> getStatusById(String id) {
        return mysqlServiceClient.getStatusById(id)
                .onErrorResume(e -> {
                    return Mono.empty();
                });
    }

    public Mono<Status> createStatus(Status status) {
        return mysqlServiceClient.createStatus(status)
                .onErrorResume(e -> {
                    return Mono.error(e);
                });
    }
}
