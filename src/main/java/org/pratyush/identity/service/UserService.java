package org.pratyush.identity.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.pratyush.identity.model.dto.User;
import org.pratyush.identity.model.request.AuthRequest;
import org.pratyush.identity.model.request.UserRegistrationRequest;
import org.pratyush.identity.model.response.AuthResponse;
import org.pratyush.identity.model.response.UserDetailResponse;
import org.pratyush.identity.model.response.UserRegistrationResponse;
import org.pratyush.identity.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final Mapper mapper;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, Mapper mapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

    public Mono<UserRegistrationResponse> register(UserRegistrationRequest request) {
        return Mono.just(request)
                .doOnNext(this::updateEncodedPassword)
                .flatMap(repository::register)
                .map(this::buildResponse);
    }

    public Mono<AuthResponse> getToken(AuthRequest request) {
        return Mono.just(request)
                .map(AuthRequest::getEmail)
                .flatMap(repository::findByEmail)
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> new AuthResponse(jwtUtil.generateToken(user)))
                .switchIfEmpty(Mono.empty());
    }

    public Mono<UserDetailResponse> getUserDetail() {
        return Mono.just(SecurityContextHolder.getContext().getAuthentication().getName())
                .map(repository::findByEmail)
                .flatMap(this::toUserDetailResponse);
    }

    private Mono<UserDetailResponse> toUserDetailResponse(Mono<User> user) {
        return user.map(u -> mapper.map(u, UserDetailResponse.class));
    }

    private UserRegistrationResponse buildResponse(Integer i) {
        return UserRegistrationResponse.builder().successful(i > 0).build();
    }

    private void updateEncodedPassword(UserRegistrationRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
    }
}
