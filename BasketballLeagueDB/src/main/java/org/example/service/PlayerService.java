package org.example.service;

import entity.Player;
import java.util.List;

public interface PlayerService {

    List<Player> getAllPlayers();

    Player getPlayerById(Integer id);

    void savePlayer(Player player);

    void deletePlayer(Integer id);
}