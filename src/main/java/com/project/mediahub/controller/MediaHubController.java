package com.project.mediahub.controller;

import com.project.mediahub.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MediaHubController {
    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public String register() {
        return "auth/register.html";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login.html";
    }

    @GetMapping("/")
    public String index(Principal principal) {
        return Optional.ofNullable(principal)
                .map(p -> "index.html")
                .orElse("auth/login.html");
    }

}
