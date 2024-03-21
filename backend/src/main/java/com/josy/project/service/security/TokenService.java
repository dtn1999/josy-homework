package com.josy.project.service.security;

import com.josy.project.model.entity.BlackListedToken;
import com.josy.project.repository.BlackListedTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final BlackListedTokenRepository repository;

    public void blackListToken(String token) {
        repository.findByToken(token)
                .ifPresentOrElse(
                        t -> {
                            log.info("Token already blacklisted");
                        },
                        () -> repository.save(
                                BlackListedToken.builder()
                                        .token(token)
                                        .build()
                        ));
    }

    public boolean isTokenBlackListed(String token) {
        return repository.findByToken(token).isPresent();
    }

}
