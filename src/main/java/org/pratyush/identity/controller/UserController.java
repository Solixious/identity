package org.pratyush.identity.controller;

import org.pratyush.identity.model.request.AuthRequest;
import org.pratyush.identity.model.request.UserRegistrationRequest;
import org.pratyush.identity.model.response.AuthResponse;
import org.pratyush.identity.model.response.HealthResponse;
import org.pratyush.identity.model.response.UserDetailResponse;
import org.pratyush.identity.model.response.UserRegistrationResponse;
import org.pratyush.identity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/identity/register")
    public Mono<ResponseEntity<UserRegistrationResponse>> register(@RequestBody Mono<UserRegistrationRequest> request) {
        return request.flatMap(userService::register)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/identity/login")
    public Mono<ResponseEntity<AuthResponse>> getToken(@RequestBody Mono<AuthRequest> request) {
        return request.flatMap(userService::getToken)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @GetMapping("/identity/")
    public Mono<ResponseEntity<UserDetailResponse>> getUserDetail() {
        return userService.getUserDetail()
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @GetMapping("/identity/health")
    public Mono<ResponseEntity<HealthResponse>> health() {
        return Mono.just(new HealthResponse(true)).map(ResponseEntity::ok);
    }
}
