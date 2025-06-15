package org.example.repository;

import entity.Player;
import entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    List<Player> findByTeamId(Integer teamId);
    List<Player> findAllByTeamInAndIsActive(List<Team> teams, String isActive);
}