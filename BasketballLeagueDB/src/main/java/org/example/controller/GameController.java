package org.example.controller;

import entity.Game;
import entity.GameStats;
import entity.Player;
import entity.Season;
import jakarta.validation.ConstraintViolationException;
import org.example.repository.GameStatsRepository;
import org.example.repository.PlayerRepository;
import org.example.service.GameService;
import org.example.service.SeasonService;
import org.example.service.TeamRecordService;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private SeasonService seasonService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameStatsRepository gameStatsRepository;
    @Autowired
    private TeamRecordService teamRecordService;

    @GetMapping("/{id}")
    public String getGameDetails(@PathVariable Integer id, Model model) {
        Game game = gameService.getGameById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
        model.addAttribute("game", game);
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
        if (game.getHomeTeam() != null && game.getAwayTeam() != null &&
                Objects.equals(game.getHomeTeam().getId(), game.getAwayTeam().getId())) {
            bindingResult.rejectValue("awayTeam", "error.game", "Drużyna gości musi być inna niż gospodarzy.");
        }

        if (game.getSeason() != null && game.getGameDate() != null) {
            Season season = seasonService.getSeasonById(game.getSeason().getId());
            LocalDateTime gameDate = game.getGameDate();
            LocalDateTime seasonStart = season.getStartDate().atStartOfDay();
            LocalDateTime seasonEnd = (season.getEndDate() != null) ? season.getEndDate().atTime(LocalTime.MAX) : seasonStart.plusYears(1);

            if (gameDate.isBefore(seasonStart) || gameDate.isAfter(seasonEnd)) {
                bindingResult.rejectValue("gameDate", "error.game", "Data meczu musi zawierać się w datach sezonu.");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.getAllTeams());
            model.addAttribute("seasons", seasonService.getAllSeasons());
            return "games/add-game";
        }

        gameService.saveGame(game);
        return "redirect:/";
    }

    @GetMapping("/stats/{id}")
    public String showGameStatsPage(@PathVariable Integer id, Model model) {
        Game game = gameService.getGameById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));

        List<Player> homePlayers = playerRepository.findByTeamId(game.getHomeTeam().getId());
        List<Player> awayPlayers = playerRepository.findByTeamId(game.getAwayTeam().getId());

        Map<Integer, GameStats> statsMap;
        if ("S".equals(game.getStatus())) {
            statsMap = new HashMap<>();
        } else {
            statsMap = gameStatsRepository.findByGameId(game.getId()).stream()
                    .collect(Collectors.toMap(stat -> stat.player.getId(), stat -> stat));
        }

        model.addAttribute("game", game);
        model.addAttribute("homePlayers", homePlayers);
        model.addAttribute("awayPlayers", awayPlayers);
        model.addAttribute("statsMap", statsMap);

        model.addAttribute("statNames", Arrays.asList("rebounds", "assists", "steals", "blocks", "turnovers", "fouls"));

        return "game-stats";
    }

    @GetMapping("/live/{id}")
    public String redirectToStatsPage(@PathVariable Integer id) {
        return "redirect:/games/stats/" + id;
    }

    @PostMapping("/live/update-stat")
    public String updateStat(@RequestParam Integer gameId,
                             @RequestParam Integer playerId,
                             @RequestParam String statName,
                             @RequestParam int change) {
        gameService.updatePlayerStat(gameId, playerId, statName, change);
        return "redirect:/games/stats/" + gameId;
    }

    @PostMapping("/start/{id}")
    public String startGame(@PathVariable Integer id) {
        gameService.startGame(id);
        return "redirect:/games/stats/" + id;
    }

    @PostMapping("/finish/{id}")
    public String finishGame(@PathVariable Integer id) {
        gameService.finishGame(id);
        teamRecordService.updateRecordsAfterGame(id);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteGame(@PathVariable Integer id) {
        gameService.deleteGameById(id);
        return "redirect:/";
    }
}