package com.josy.project.controller;

import com.josy.project.model.payload.ApiResponse;
import com.josy.project.model.payload.LoginRequest;
import com.josy.project.model.payload.RegistrationRequest;
import com.josy.project.model.payload.ResetPasswordRequest;
import com.josy.project.service.security.AuthenticationService;
import com.josy.project.service.security.TokenService;
import com.josy.project.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SecurityController {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegistrationRequest request) {
        log.info("Registering user with the following information: {}", request);
        ApiResponse registrationResponse = authenticationService.register(request);
        log.info("User registered successfully");
        return ResponseEntity.ok(registrationResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest request) {
        log.info("Logging in user with the following information: {}", request);
        ApiResponse loginResponse = authenticationService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(final HttpServletRequest request) {
        log.info("Logging out user with the following information");
        JwtTokenUtil.extractTokenFromRequest(request).ifPresent(tokenService::blackListToken);
        ApiResponse logoutResponse = ApiResponse.success("User logged out successfully", null);
        return ResponseEntity.ok(logoutResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> me(@AuthenticationPrincipal UserDetails userDetails) {
        if (Objects.isNull(userDetails)) {
            throw new AuthenticationServiceException("User not found");
        }
        return ResponseEntity
                .ok(this.authenticationService.me(userDetails));
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        log.info("Resetting password for user with the following information: {}", request);
        ApiResponse resetPasswordResponse = authenticationService.resetPassword(request);
        return ResponseEntity.ok(resetPasswordResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Deleting account for user with the following information: {}", userDetails);
        ApiResponse deleteAccountResponse = authenticationService.deleteAccount(userDetails);
        return ResponseEntity.ok(deleteAccountResponse);
    }

}
