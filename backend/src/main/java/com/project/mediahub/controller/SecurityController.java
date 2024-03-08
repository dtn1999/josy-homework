package com.project.mediahub.controller;

import com.project.mediahub.model.payload.ApiResponse;
import com.project.mediahub.model.payload.LoginRequest;
import com.project.mediahub.model.payload.RegistrationRequest;
import com.project.mediahub.model.payload.ResetPasswordRequest;
import com.project.mediahub.service.security.AuthenticationService;
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

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegistrationRequest request) {
        log.info("Registering user with the following information: {}", request);
        ApiResponse registrationResponse = authenticationService.register(request);
        return ResponseEntity.ok(registrationResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest request) {
        log.info("Logging in user with the following information: {}", request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        log.info("Logging out user with the following information");
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PutMapping
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        log.info("Resetting password for user with the following information: {}", request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

}
