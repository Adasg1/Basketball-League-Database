package org.example.controller;

import entity.Season;
import org.example.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/seasons")
public class SeasonController {

    @Autowired
    private SeasonService seasonService;

    @GetMapping
    public String listSeasons(Model model) {
        model.addAttribute("listSeasons", seasonService.getAllSeasons());
        return "seasons/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("season", new Season());
        model.addAttribute("formTitle", "Add New Season");
        return "seasons/form";
    }

    @PostMapping("/save")
    public String saveSeason(@ModelAttribute("season") Season season, Model model) {

        if (season.getStartDate() != null && season.getStartDate().getYear() != season.getSeasonYear()) {
            model.addAttribute("errorMessage", "Start date must be in " + season.getSeasonYear());
            return "seasons/form";
        }

        seasonService.saveSeason(season);
        return "redirect:/seasons";
    }

    @GetMapping("/edit/{id}")
    public String editSeason(@PathVariable Integer id, Model model) {
        model.addAttribute("season", seasonService.getSeasonById(id));
        model.addAttribute("formTitle", "Edit Season");
        return "seasons/form";
    }

    @PostMapping("/delete/{id}")
    public String deleteSeason(@PathVariable Integer id) {
        seasonService.deleteSeason(id);
        return "redirect:/seasons";
    }
}