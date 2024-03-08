package com.project.mediahub.service.security;

import com.project.mediahub.model.entity.BlackListedToken;
import com.project.mediahub.repository.BlackListedTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
