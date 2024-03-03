package com.project.mediahub.service;

import com.project.mediahub.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;

    public void register(final RegistrationRequest request) {

    }

    public void login(final String username, final String password) {

    }
}
