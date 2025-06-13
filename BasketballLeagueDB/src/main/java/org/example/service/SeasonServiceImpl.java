package org.example.service;

import entity.Season;
import org.example.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonRepository seasonRepository;

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
    public void saveSeason(Season season) {
        seasonRepository.save(season);
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
}