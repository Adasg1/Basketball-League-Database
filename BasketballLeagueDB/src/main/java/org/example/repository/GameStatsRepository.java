package org.example.repository;

import entity.Game;
import entity.GameStats;
import entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface GameStatsRepository extends JpaRepository<GameStats, Integer> {
    Optional<GameStats> findByGameAndPlayer(Game game, Player player);
    List<GameStats> findByGameId(Integer gameId);
}