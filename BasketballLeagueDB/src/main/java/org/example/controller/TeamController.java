package org.example.controller;

import entity.Team;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

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
}