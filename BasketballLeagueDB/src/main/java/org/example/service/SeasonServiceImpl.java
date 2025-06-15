package org.example.service;

import entity.*;
import jakarta.transaction.Transactional;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private TeamRecordRepository teamRecordRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerTeamRepository playerTeamRepository;

    @Override
    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    @Override
    public Season getSeasonById(Integer id) {
        return seasonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Season not found"));
    }

    @Override
    @Transactional
    public void saveSeason(Season season) {
        // 1. Zapisz sezon i uzyskaj jego ID
        Season savedSeason = seasonRepository.save(season);

        // 2. Pobierz aktywne drużyny
        List<Team> activeTeams = teamRepository.findByIsActive("Y");

        // 3. Utwórz rekordy drużyn
        for (Team team : activeTeams) {
            TeamRecord record = new TeamRecord(team, savedSeason, 0, 0, 0, 0, 0, 0);
            teamRecordRepository.save(record);
        }

        List<Player> activePlayers = playerRepository.findAllByTeamInAndIsActive(activeTeams, "Y");
        for (Player player : activePlayers) {
            PlayerTeam pt = new PlayerTeam();
            pt.setPlayer(player);
            pt.setTeam(player.getTeam());
            pt.setSeasonId(savedSeason.getId());
            pt.setJerseyNumber(player.getJerseyNumber());
            pt.setStartDate(LocalDate.now());
            playerTeamRepository.save(pt);
        }
    }

    @Override
    public void deleteSeason(Integer id) {
        seasonRepository.deleteById(id);
    }

    public List<Season> findAll() {
        return seasonRepository.findAll();
    }

    public Integer getCurrentSeasonId() {
        return seasonRepository.findTopByOrderBySeasonYearDesc()
                .map(Season::getId)
                .orElseThrow(() -> new RuntimeException("No season found"));
    }

    @Override
    public Season getCurrentSeason() {
        Integer id = getCurrentSeasonId();
        return seasonRepository.findById(id).orElseThrow(() -> new RuntimeException("Current season not found"));
    }
}