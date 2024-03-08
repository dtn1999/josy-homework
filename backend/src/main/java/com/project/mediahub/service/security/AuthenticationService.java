package com.project.mediahub.service.security;

import com.project.mediahub.model.payload.ApiResponse;
import com.project.mediahub.model.payload.AuthResponse;
import com.project.mediahub.model.payload.RegistrationRequest;
import com.project.mediahub.model.payload.ResetPasswordRequest;
import com.project.mediahub.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
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

    public ApiResponse login(final String username, final String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authentication = this.authenticationProvider.authenticate(token);
            if (authentication.isAuthenticated()) {
                AuthResponse accessToken = generateAuthResponse(username);
                return ApiResponse.success("User logged in successfully", accessToken);
            }
            log.warn("{} not authenticated", username);
            return ApiResponse.failure("User not authenticated");
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }


    public ApiResponse resetPassword(final ResetPasswordRequest request) {
        UserDetails userDetails = this.userService.loadUserByUsername(request.getEmail());
        if (userDetails == null) {
            return ApiResponse.failure("User not found");
        }
        this.userService.updatePassword(userDetails, request.getPassword());
        AuthResponse accessToken = generateAuthResponse(request.getEmail());
        return ApiResponse.success("Password reset successfully", accessToken);
    }


    public AuthResponse generateAuthResponse(String username) {
        String accessToken = JwtTokenUtil.generateToken(username);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .build();
    }

}
