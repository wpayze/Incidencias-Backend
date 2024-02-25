package com.incidenciasvlc.authservice.service;

import com.incidenciasvlc.authservice.client.MysqlServiceClient;
import com.incidenciasvlc.authservice.model.User;
import com.incidenciasvlc.authservice.security.JwtTokenService;
import com.incidenciasvlc.authservice.security.JwtTokenUtil;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final MysqlServiceClient mysqlServiceClient;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(MysqlServiceClient mysqlServiceClient) {
        this.mysqlServiceClient = mysqlServiceClient;
    }

    public Mono<User> registerUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return mysqlServiceClient.createUser(user);
    }

    public Mono<User> getUserByEmail(String email) {
        return mysqlServiceClient.getUserByEmail(email);
    }

    public Mono<ResponseEntity<?>> validateLoginAndGenerateToken(String email, String password) {
        return mysqlServiceClient.getUserByEmail(email)
                .flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        user.setPassword(null);
                        String token = jwtTokenService.generateToken(user);
                        return Mono.just(ResponseEntity.ok().body(Map.of("token", token)));
                    } else {
                        return Mono.just(ResponseEntity.status(401).body("Credenciales incorrectas"));
                    }
                })
                .defaultIfEmpty(ResponseEntity.status(404).body("Usuario no encontrado"));
    }

    public Mono<Boolean> validateToken(String token) {
        try {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            return Mono.just(jwtTokenUtil.validateToken(token, username));
        } catch (Exception e) {
            return Mono.just(false);
        }
    }
}
