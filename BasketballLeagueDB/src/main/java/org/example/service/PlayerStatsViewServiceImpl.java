package org.example.service;

import entity.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerStatsViewServiceImpl implements PlayerStatsViewService {

    @Autowired
    private PlayerStatsViewRepository repository;

    @Override
    public List<PlayerStatsView> getAll() {
        return repository.findAll();
    }

    @Override
    public List<PlayerStatsView> getByPlayerId(Integer playerId) {
        return repository.findByPlayerId(playerId);
    }

    @Override
    public List<PlayerStatsView> getBySeasonId(Integer seasonId) {
        return repository.findBySeasonId(seasonId);
    }

    @Override
    public List<PlayerStatsView> getByTeamId(Integer teamId) {
        return repository.findByTeamId(teamId);
    }

    @Override
    public List<PlayerStatsView> getByPlayerIdAndSeasonId(Integer playerId, Integer seasonId) {
        return repository.findByPlayerIdAndSeasonId(playerId, seasonId);
    }
}