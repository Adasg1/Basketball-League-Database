package org.example.controller;

import entity.Season;
import entity.Team;
import entity.TeamRecord;
import org.example.service.SeasonService;
import org.example.service.TeamRecordService;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRecordService teamRecordService;

    @Autowired
    private SeasonService seasonService;

    // READ (List All)
    @GetMapping
    public String listTeams(Model model) {
        model.addAttribute("listTeams", teamService.getAllTeams());
        return "teams/list";
    }

    // CREATE (Show Form)
    @GetMapping("/new")
    public String showNewTeamForm(Model model) {
        model.addAttribute("team", new Team());
        model.addAttribute("formTitle", "Add New Team");
        return "teams/form";
    }

    // UPDATE (Show Form)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Team team = teamService.getTeamById(id);
        model.addAttribute("team", team);
        model.addAttribute("formTitle", "Edit Team");
        return "teams/form";
    }

    // CREATE and UPDATE (Process Form)
    @PostMapping("/save")
    public String saveTeam(@ModelAttribute("team") Team team) {
        teamService.saveTeam(team);
        return "redirect:/teams";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable Integer id) {
        teamService.deleteTeam(id);
        return "redirect:/teams";
    }

    @GetMapping("/standings")
    public String showStandings(@RequestParam(value = "seasonId", required = false) Integer seasonId, Model model) {
        List<Season> allSeasons = seasonService.getAllSeasons();
        model.addAttribute("seasons", allSeasons);

        if (seasonId == null && !allSeasons.isEmpty()) {
            // Znajdź sezon o najwyższym roku
            seasonId = allSeasons.stream()
                    .max(Comparator.comparing(Season::getSeasonYear))
                    .map(Season::getId)
                    .orElse(null);
        }

        List<TeamRecord> teamRecords = (seasonId != null)
                ? teamRecordService.getRecordsBySeasonId(seasonId)
                : List.of(); // pusto jeśli sezonów brak

        model.addAttribute("teamRecords", teamRecords);
        model.addAttribute("selectedSeasonId", seasonId);
        return "teams/standings";
    }
}