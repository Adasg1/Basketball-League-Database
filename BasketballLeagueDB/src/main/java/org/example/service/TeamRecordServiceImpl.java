package org.example.service;

import entity.Game;
import entity.Season;
import entity.Team;
import entity.TeamRecord;
import jakarta.persistence.EntityNotFoundException;
import org.example.repository.GameRepository;
import org.example.repository.TeamRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamRecordServiceImpl implements TeamRecordService {

    @Autowired
    private TeamRecordRepository teamRecordRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SeasonService seasonService;

    @Override
    public void updateRecordsAfterGame(Integer gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with id: " + gameId));

        Team home = game.getHomeTeam();
        Team away = game.getAwayTeam();
        Season season = game.getSeason();

        TeamRecord homeRecord = teamRecordRepository
                .findByTeamAndSeason(home, season)
                .orElseGet(() -> new TeamRecord(home, season, 0, 0, 0, 0, 0, 0));

        TeamRecord awayRecord = teamRecordRepository
                .findByTeamAndSeason(away, season)
                .orElseGet(() -> new TeamRecord(away, season, 0, 0, 0, 0, 0, 0));

        boolean homeWon = game.getHomeScore() > game.getAwayScore();

        if (homeWon) {
            homeRecord.incrementWins();
            homeRecord.incrementHomeWins();

            awayRecord.incrementLosses();
            awayRecord.incrementAwayLosses();
        } else {
            awayRecord.incrementWins();
            awayRecord.incrementAwayWins();

            homeRecord.incrementLosses();
            homeRecord.incrementHomeLosses();
        }

        teamRecordRepository.save(homeRecord);
        teamRecordRepository.save(awayRecord);
    }

    private double getWinPercentage(TeamRecord r) {
        int wins = r.getWins();
        int losses = r.getLosses();
        return (wins + losses) == 0 ? 0 : (double) wins / (wins + losses);
    }

    @Override
    public List<TeamRecord> getAllTeamRecords() {
        return teamRecordRepository.findAll().stream()
                .sorted((r1, r2) -> Double.compare(
                        getWinPercentage(r2), getWinPercentage(r1)
                ))
                .toList();
    }

    @Override
    public List<TeamRecord> getRecordsBySeasonId(Integer seasonId) {
        Season season = seasonService.getSeasonById(seasonId);
        return teamRecordRepository.findBySeason(season).stream()
                .sorted((r1, r2) -> Double.compare(
                        getWinPercentage(r2), getWinPercentage(r1)
                ))
                .toList();
    }
}