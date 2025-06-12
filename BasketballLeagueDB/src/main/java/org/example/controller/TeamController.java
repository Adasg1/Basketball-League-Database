package org.example.controller;

import entity.Team;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
        // 1. Get the team from the database
        Team team = teamService.getTeamById(id);
        // 2. Add it to the model to pre-populate the form
        model.addAttribute("team", team);
        model.addAttribute("formTitle", "Edit Team");
        // 3. Show the same form
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

    @GetMapping("/test-lock/{id}")
    @ResponseBody
    public String testOptimisticLock(@PathVariable Integer id) {
        StringBuilder response = new StringBuilder();
        response.append("--- Test optymistycznego locka (z odlaczeniem) ---<br/>");

        // 1. "User 1" czyta team z bazy danych.
        response.append("User 1 pobiera team o ID: ").append(id).append("<br/>");
        Team user1Copy = teamService.getTeamById(id);
        response.append("User 1 ma team w wersji: ").append(user1Copy.getVersion()).append("<br/>");

        teamService.detach(user1Copy);
        response.append("Kopia Usera 1 zostala ODPIETA.<br/><hr/>");

        // 2. "User 2" TEZ czyta ten sam team. To bedzie swiezy obiekt z bazy.
        response.append("User 2 pobiera TEN SAM team o ID: ").append(id).append("<br/>");
        Team user2Copy = teamService.getTeamById(id);
        response.append("User 2 ma team w wersji: ").append(user2Copy.getVersion()).append("<br/>");

        // TEGO TEZ ODPINAMY!
        teamService.detach(user2Copy);
        response.append("Kopia Usera 2 zostala ODPIETA.<br/><hr/>");

        // 3. User 1 robi zmiane i zapisuje. To powinno sie udac.
        response.append("User 1 zmienia arene i zapisuje...<br/>");
        user1Copy.setArena("Zmienione przez Usera 1");
        teamService.saveTeam(user1Copy);
        response.append("Zapis Usera 1 udany.<br/><hr/>");

        // 4. TERAZ, User 2 probuje zrobic swoja zmiane i ja zapisac. To MUSI sie nie udac.
        response.append("User 2 zmienia arene i probuje zapisac...<br/>");
        user2Copy.setArena("Zmienione przez Usera 2");

        try {
            teamService.saveTeam(user2Copy);
            // Jesli kod tu dojdzie, to znaczy, ze lock NIE zadzialal.
            response.append("<strong style='color:red;'>TEST NIEUDANY! Optymistyczny lock nie zapobiegl aktualizacji.</strong>");
        } catch (ObjectOptimisticLockingFailureException e) {
            // Jesli zlapalismy ten wyjatek, to znaczy, ze lock DZIALA! O to chodzilo.
            response.append("<strong style='color:green;'>SUKCES! Optymistyczny lock ZADZIALAL!</strong><br/>");
            response.append("Aplikacja poprawnie rzucila wyjatkiem ObjectOptimisticLockingFailureException.<br/>");
        } catch (Exception e) {
            response.append("<strong style='color:red;'>TEST NIEUDANY, nieoczekiwany wyjatek: </strong>").append(e.getMessage());
        }

        return response.toString();
    }
}