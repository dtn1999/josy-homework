package com.project.mediahub.controller;

import com.project.mediahub.model.RegistrationRequest;
import com.project.mediahub.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MediaHubController {
    private final AuthenticationService authenticationService;

    @GetMapping("/auth/register")
    public String register(Model model) {
        model.addAttribute("registration", new RegistrationRequest());
        return "auth/register.html";
    }

    @PostMapping("/auth/register")
    public String register(@ModelAttribute("registration") RegistrationRequest request) {
        authenticationService.register(request);
        return "redirect:/auth//register?success";
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
