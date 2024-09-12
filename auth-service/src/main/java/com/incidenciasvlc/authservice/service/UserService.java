package com.incidenciasvlc.authservice.service;

import com.incidenciasvlc.authservice.client.MysqlServiceClient;
import com.incidenciasvlc.authservice.model.User;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final MysqlServiceClient mysqlServiceClient;

    public UserService(MysqlServiceClient mysqlServiceClient) {
        this.mysqlServiceClient = mysqlServiceClient;
    }

    public Flux<User> getAllUsers() {
        return mysqlServiceClient.getAllUsers();
    }

    public Mono<User> getUserById(String id) {
        return mysqlServiceClient.getUserById(id);
    }

    public Mono<User> getUserByEmail(String email) {
        return mysqlServiceClient.getUserByEmail(email);
    }

    public Mono<User> updateUser(User user) {
        return mysqlServiceClient.updateUser(user);
    }
}
