package org.example.service;

import entity.Season;

import java.util.Arrays;
import java.util.List;

public interface SeasonService {
    List<Season> getAllSeasons();
    Season getSeasonById(Integer id);
    void saveSeason(Season season);
    void deleteSeason(Integer id);
    List<Season> findAll();
    Integer getCurrentSeasonId();
}