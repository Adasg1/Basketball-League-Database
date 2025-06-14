package org.example.controller;

import entity.Game;
import entity.Season;
import org.example.service.SeasonService;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.example.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private SeasonService seasonService;

    @GetMapping("/{id}")
    public String getGameDetails(@PathVariable Integer id, Model model) {
        gameService.getGameById(id).ifPresent(game -> model.addAttribute("game", game));
        return "game-details";
    }

    @GetMapping("/add")
    public String showAddGameForm(Model model) {
        model.addAttribute("game", new Game());
        model.addAttribute("teams", teamService.getAllTeams());
        model.addAttribute("seasons", seasonService.getAllSeasons());
        return "games/add-game";
    }

    @PostMapping("/add")
    public String addGame(@ModelAttribute("game") Game game, BindingResult bindingResult, Model model) {

        if (game.getHomeTeam().equals(game.getAwayTeam())) {
            bindingResult.rejectValue("awayTeam", "error.game", "Drużyny muszą być różne");
        }

        Season season = game.getSeason();
        LocalDateTime gameDate = game.getGameDate();

        LocalDateTime seasonStart = season.getStartDate().atStartOfDay();
        LocalDateTime seasonEnd = season.getEndDate().atTime(LocalTime.MAX);

        if (gameDate.isBefore(seasonStart) || gameDate.isAfter(seasonEnd)) {
            bindingResult.rejectValue("gameDate", "error.game", "Data meczu musi zawierać się w sezonie");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.getAllTeams());
            model.addAttribute("seasons", seasonService.getAllSeasons());
            return "add-game";
        }

        gameService.saveGame(game);
        return "redirect:/";
    }
}