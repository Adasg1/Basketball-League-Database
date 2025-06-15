package org.example.repository;

import entity.PlayerStatsView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerStatsViewRepository extends JpaRepository<PlayerStatsView, Integer> {

    List<PlayerStatsView> findByPlayerId(Integer playerId);

    List<PlayerStatsView> findBySeasonId(Integer seasonId);

    List<PlayerStatsView> findByTeamId(Integer teamId);

    List<PlayerStatsView> findByPlayerIdAndSeasonId(Integer playerId, Integer seasonId);
}