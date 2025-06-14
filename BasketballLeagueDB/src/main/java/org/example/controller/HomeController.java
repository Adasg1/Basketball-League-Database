package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.example.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private GameService gameService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "index";
    }
}