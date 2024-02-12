package com.project.mediahub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MediaHubController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
