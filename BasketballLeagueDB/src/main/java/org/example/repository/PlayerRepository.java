package org.example.repository;

import entity.Player;
import entity.Team;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    List<Player> findByTeamId(Integer teamId);
    List<Player> findAllByTeamInAndIsActive(List<Team> teams, String isActive);
}