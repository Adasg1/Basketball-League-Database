package org.example.service;

import entity.Season;
import entity.Team;
import entity.TeamRecord;
import jakarta.transaction.Transactional;
import org.example.repository.SeasonRepository;
import org.example.repository.TeamRecordRepository;
import org.example.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private TeamRecordRepository teamRecordRepository;

    @Autowired
    private TeamRepository teamRepository;

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