package com.project.mediahub.controller;

import com.project.mediahub.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MediaHubController {
    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
