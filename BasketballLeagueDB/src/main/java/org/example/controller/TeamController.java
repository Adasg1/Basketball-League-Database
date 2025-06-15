package org.example.controller;

import entity.Player;
import entity.Season;
import entity.Team;
import entity.TeamRecord;
import org.example.service.SeasonService;
import org.example.service.TeamRecordService;
import org.example.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamRecordService teamRecordService;
    private final SeasonService seasonService;

    public TeamController(TeamService teamService, TeamRecordService teamRecordService, SeasonService seasonService) {
        this.teamService = teamService;
        this.teamRecordService = teamRecordService;
        this.seasonService = seasonService;
    }

    @GetMapping
    public String listTeams(Model model) {
        model.addAttribute("listTeams", teamService.getAllTeams());
        return "teams/list";
    }

    @GetMapping("/new")
    public String showNewTeamForm(Model model) {
        model.addAttribute("team", new Team());
        model.addAttribute("formTitle", "Add New Team");
        return "teams/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Team team = teamService.getTeamById(id);
        model.addAttribute("team", team);
        model.addAttribute("formTitle", "Edit Team");
        return "teams/form";
    }

    @PostMapping("/save")
    public String saveTeam(@ModelAttribute("team") Team team) {
        teamService.saveTeam(team);
        return "redirect:/teams";
    }

    @PostMapping("/delete/{id}")
    public String deleteTeam(@PathVariable Integer id) {
        teamService.deleteTeam(id);
        return "redirect:/teams";
    }

    @GetMapping("/standings")
    public String showStandings(@RequestParam(value = "seasonId", required = false) Integer seasonId, Model model) {
        List<Season> allSeasons = seasonService.getAllSeasons();
        model.addAttribute("seasons", allSeasons);

        if (allSeasons.isEmpty()) {
            model.addAttribute("teamRecords", List.of());
            model.addAttribute("selectedSeasonId", null);
            return "teams/standings";
        }

        Integer selectedSeasonId = (seasonId != null)
                ? seasonId
                : seasonService.getCurrentSeasonId();

        List<TeamRecord> teamRecords = (selectedSeasonId != null)
                ? teamRecordService.getRecordsBySeasonIdOrderByWinsDesc(selectedSeasonId)
                : List.of();

        model.addAttribute("teamRecords", teamRecords);
        model.addAttribute("selectedSeasonId", selectedSeasonId);
        return "teams/standings";
    }

    @GetMapping("/{id}/players")
    public String viewTeamPlayers(@PathVariable Integer id, Model model) {
        Team team = teamService.getTeamById(id);
        List<Player> players = team.getPlayers();

        model.addAttribute("team", team);
        model.addAttribute("players", players);
        return "teams/players";
    }
}