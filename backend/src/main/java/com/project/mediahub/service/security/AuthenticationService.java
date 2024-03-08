package com.project.mediahub.service.security;

import com.project.mediahub.model.payload.ApiResponse;
import com.project.mediahub.model.payload.AuthResponse;
import com.project.mediahub.model.payload.RegistrationRequest;
import com.project.mediahub.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final DaoAuthenticationProvider authenticationProvider;

    public ApiResponse register(@Valid final RegistrationRequest request) {
        this.userService.register(request);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        try {
            this.authenticationProvider.authenticate(token);
            String accessToken = JwtTokenUtil.generateToken(request.getEmail());

            AuthResponse authResponse = AuthResponse.builder()
                    .accessToken(accessToken)
                    .build();

            return ApiResponse.builder()
                    .message("User registered successfully")
                    .success(true)
                    .data(authResponse)
                    .build();

        } catch (Exception e) {
            return ApiResponse.builder()
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    public void login(final String username, final String password) {

    }
}
