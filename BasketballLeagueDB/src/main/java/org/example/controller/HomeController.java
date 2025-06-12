package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // handlujemy request do strony głównej localhost:8080/
    @GetMapping("/")
    public String home() {
        return "index"; // /templates/index.html
    }
}