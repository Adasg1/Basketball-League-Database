package spring.service;

import entity.PlayerStatsView;

import java.util.List;

public interface PlayerStatsViewService {
    List<PlayerStatsView> getByPlayerId(Integer playerId);
}