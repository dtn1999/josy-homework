package com.project.mediahub.controller;

import com.project.mediahub.model.ApiResponse;
import com.project.mediahub.model.RegistrationRequest;
import com.project.mediahub.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MediaHubController {
    private final AuthenticationService authenticationService;

    @GetMapping("/auth/register")
    public String register(Model model) {
        model.addAttribute("registrationInfo", new RegistrationRequest());
        return "auth/register";
    }

    @PostMapping("/auth/register")
    public String register(
            @Valid RegistrationRequest request,
            BindingResult bindingResult,
            Model model) {
        log.info("Registering user with the following information: {}", request);
        model.addAttribute("registrationInfo", request);
        if (bindingResult.hasErrors()) {
            log.error("Registration request has errors: {}", bindingResult.getAllErrors());
            return "auth/register";
        }
        ApiResponse registrationResponse = authenticationService.register(request);
        model.addAttribute("response", registrationResponse);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/")
    public String index(Principal principal) {
       return "index";
    }

    @GetMapping("/create-notice")
    public String createNotice() {
        return "create-notice";
    }
}
