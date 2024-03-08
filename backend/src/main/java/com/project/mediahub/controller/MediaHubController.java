package com.project.mediahub.controller;

import com.project.mediahub.model.payload.ApiResponse;
import com.project.mediahub.model.payload.LoginRequest;
import com.project.mediahub.model.payload.RegistrationRequest;
import com.project.mediahub.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MediaHubController {
    private final AuthenticationService authenticationService;

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegistrationRequest request) {
        log.info("Registering user with the following information: {}", request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest request) {
        log.info("Logging in user with the following information: {}", request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PostMapping("/notes")
    public String index(Principal principal) {
       return "index";
    }

    @GetMapping("/create-notice")
    public String createNotice() {
        return "create-notice";
    }
}
