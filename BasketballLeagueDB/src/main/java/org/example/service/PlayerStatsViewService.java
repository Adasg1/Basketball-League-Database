package org.example.service;

import entity.PlayerStatsView;

import java.util.List;

public interface PlayerStatsViewService {
    List<PlayerStatsView> getAll();
    List<PlayerStatsView> getByPlayerId(Integer playerId);
    List<PlayerStatsView> getBySeasonId(Integer seasonId);
    List<PlayerStatsView> getByTeamId(Integer teamId);
    List<PlayerStatsView> getByPlayerIdAndSeasonId(Integer playerId, Integer seasonId);
}