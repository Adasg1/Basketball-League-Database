package org.example.repository;

import entity.PlayerTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerTeamRepository extends JpaRepository<PlayerTeam, Integer>  {
    PlayerTeam findTopByPlayerIdOrderByStartDateDesc(Integer playerId);
}
