package org.example.service;

import entity.Game;
import java.util.List;
import java.util.Optional;

public interface GameService {
    List<Game> getAllGames();
    Optional<Game> getGameById(Integer id);
    void saveGame(Game game);
    void deleteGameById(Integer id);
    void startGame(Integer gameId);
    void finishGame(Integer gameId);
    void updatePlayerStat(Integer gameId, Integer playerId, String statName, int change);
}