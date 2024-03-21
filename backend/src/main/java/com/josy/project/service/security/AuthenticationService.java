package com.josy.project.service.security;

import com.josy.project.model.payload.*;
import com.josy.project.model.entity.User;
import com.project.mediahub.model.payload.*;
import com.josy.project.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final DaoAuthenticationProvider authenticationProvider;

    public ApiResponse register(@Valid final RegistrationRequest request) {
        this.userService.register(request);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        try {
            this.authenticationProvider.authenticate(token);
            AuthResponse accessToken = generateAuthResponse(request.getEmail());
            return ApiResponse.success("User registered successfully", accessToken);
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

    public ApiResponse me(UserDetails userDetails) {
        User user = (User)this.userService.loadUserByUsername(userDetails.getUsername());
        return ApiResponse.success(UserInfo.from(user));
    }
}
