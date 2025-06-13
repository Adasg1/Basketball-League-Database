package org.example.controller;

import entity.Player;
import entity.Team;
import org.example.service.PlayerService;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;

    @Autowired
    public PlayerController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @GetMapping
    public String listPlayers(Model model) {
        List<Player> players = playerService.getAllPlayers();
        model.addAttribute("players", players);
        return "players/list"; // nazwa widoku (np. players/list.html)
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("player", new Player());
        model.addAttribute("teams", teamService.getAllTeams());
        return "players/form"; // formularz dodawania/edycji
    }

    @PostMapping
    public String savePlayer(@Valid @ModelAttribute("player") Player player,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.getAllTeams());
            return "players/form";
        }
        playerService.savePlayer(player);
        return "redirect:/players";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Player player = playerService.getPlayerById(id);
        model.addAttribute("player", player);
        model.addAttribute("teams", teamService.getAllTeams());
        return "players/form";
    }

    @GetMapping("/delete/{id}")
    public String deletePlayer(@PathVariable("id") Integer id) {
        playerService.deletePlayer(id);
        return "redirect:/players";
    }
}