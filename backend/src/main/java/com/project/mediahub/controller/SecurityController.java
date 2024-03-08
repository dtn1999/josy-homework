package com.project.mediahub.controller;

import com.project.mediahub.model.payload.ApiResponse;
import com.project.mediahub.model.payload.LoginRequest;
import com.project.mediahub.model.payload.RegistrationRequest;
import com.project.mediahub.model.payload.ResetPasswordRequest;
import com.project.mediahub.service.security.AuthenticationService;
import com.project.mediahub.service.security.TokenService;
import com.project.mediahub.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        log.info("Resetting password for user with the following information: {}", request);
        ApiResponse resetPasswordResponse = authenticationService.resetPassword(request);
        return ResponseEntity.ok(resetPasswordResponse);
    }

}
