package com.project.mediahub.service;

import com.project.mediahub.model.User;
import com.project.mediahub.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;

    public User register(final RegistrationRequest request) {
        return this.userService.register(request);
    }

    public void login(final String username, final String password) {

    }
}
