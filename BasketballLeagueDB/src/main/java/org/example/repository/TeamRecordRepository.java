package org.example.repository;

import entity.Season;
import entity.Team;
import entity.TeamRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRecordRepository extends JpaRepository<TeamRecord, Integer> {
    Optional<TeamRecord> findByTeam(Team home);
    Optional<TeamRecord> findByTeamAndSeason(Team home, Season season);
    List<TeamRecord> findBySeason(Season season);
    List<TeamRecord> findBySeasonIdOrderByWinsDesc(Integer seasonId);
}
