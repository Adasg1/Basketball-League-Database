package org.example.service;

import entity.*;
import org.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final GameStatsRepository gameStatsRepository;
    private final TeamRecordRepository teamRecordRepository;

    public GameServiceImpl(GameRepository gameRepository,PlayerRepository playerRepository,GameStatsRepository gameStatsRepository,TeamRecordRepository teamRecordRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.gameStatsRepository = gameStatsRepository;
        this.teamRecordRepository = teamRecordRepository;
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Optional<Game> getGameById(Integer id) {
        return gameRepository.findById(id);
    }

    @Override
    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    @Transactional
    @Override
    public void deleteGameById(Integer id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Team winner = game.getWinnerTeam();
        Team home = game.getHomeTeam();
        Team away = game.getAwayTeam();
        Season season = game.getSeason();

        if (winner != null && home != null && away != null) {
            boolean homeWon = winner.getId().equals(home.getId());
            boolean awayWon = winner.getId().equals(away.getId());

            Optional<TeamRecord> homeRecordOpt = teamRecordRepository.findByTeamAndSeason(home, season);
            Optional<TeamRecord> awayRecordOpt = teamRecordRepository.findByTeamAndSeason(away, season);

            if (homeRecordOpt.isPresent() && awayRecordOpt.isPresent()) {
                TeamRecord homeRecord = homeRecordOpt.get();
                TeamRecord awayRecord = awayRecordOpt.get();

                if (homeWon) {
                    // HOME wygrał, AWAY przegrał
                    homeRecord.setWins(Math.max(0, homeRecord.getWins() - 1));
                    homeRecord.setHomeWins(Math.max(0, homeRecord.getHomeWins() - 1));

                    awayRecord.setLosses(Math.max(0, awayRecord.getLosses() - 1));
                    awayRecord.setAwayLosses(Math.max(0, awayRecord.getAwayLosses() - 1));

                } else if (awayWon) {
                    // AWAY wygrał, HOME przegrał
                    awayRecord.setWins(Math.max(0, awayRecord.getWins() - 1));
                    awayRecord.setAwayWins(Math.max(0, awayRecord.getAwayWins() - 1));

                    homeRecord.setLosses(Math.max(0, homeRecord.getLosses() - 1));
                    homeRecord.setHomeLosses(Math.max(0, homeRecord.getHomeLosses() - 1));
                }

                teamRecordRepository.save(homeRecord);
                teamRecordRepository.save(awayRecord);
            }
        }

        // Usuń mecz
        gameRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void startGame(Integer gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));

        if (!"S".equals(game.getStatus())) {
            throw new IllegalStateException("Game can only be started if it is scheduled.");
        }

        game.setStatus("A");

        // Stwórz puste rekordy GameStats dla wszystkich graczy obu drużyn
        List<Player> homePlayers = playerRepository.findByTeamId(game.getHomeTeam().getId());
        List<Player> awayPlayers = playerRepository.findByTeamId(game.getAwayTeam().getId());

        for (Player player : homePlayers) {
            if (gameStatsRepository.findByGameAndPlayer(game, player).isEmpty()) {
                gameStatsRepository.save(new GameStats(game, player, game.getHomeTeam()));
            }
        }
        for (Player player : awayPlayers) {
            if (gameStatsRepository.findByGameAndPlayer(game, player).isEmpty()) {
                gameStatsRepository.save(new GameStats(game, player, game.getAwayTeam()));
            }
        }
        gameRepository.save(game);
    }

    @Override
    @Transactional
    public void finishGame(Integer gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));

        if (!"A".equals(game.getStatus())) {
            throw new IllegalStateException("Game can only be finished if it is active.");
        }

        game.setStatus("F");

        if (game.getHomeScore() > game.getAwayScore()) {
            game.setWinnerTeam(game.getHomeTeam());
        } else if (game.getAwayScore() > game.getHomeScore()) {
            game.setWinnerTeam(game.getAwayTeam());
        } else {
            game.setWinnerTeam(null);
        }
        gameRepository.save(game);
    }

    @Override
    @Transactional
    public void updatePlayerStat(Integer gameId, Integer playerId, String statName, int change) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

        GameStats stats = gameStatsRepository.findByGameAndPlayer(game, player)
                .orElseGet(() -> new GameStats(game, player, player.getTeam()));

        switch (statName) {
            case "points":
                // Sprawdzenie czy można odjąć punkty
                if (stats.points + change < 0) break;
                stats.points += change;
                if (stats.team.getId().equals(game.getHomeTeam().getId())) {
                    game.setHomeScore(game.getHomeScore() + change);
                } else {
                    game.setAwayScore(game.getAwayScore() + change);
                }

                // Logika dla trafionych/nietrafionych rzutów
                if (change > 0) { // Tylko przy dodawaniu punktów
                    stats.fieldGoalsMade += 1;
                    stats.fieldGoalsAttempted += 1;
                    if (change == 3) {
                        stats.threePointsMade += 1;
                        stats.threePointsAttempted += 1;
                    } else if (change == 1) {
                        stats.freeThrowsMade += 1;
                        stats.freeThrowsAttempted += 1;
                    }
                } else if (change < 0) { // Przy odejmowaniu punktów
                    stats.fieldGoalsMade -= 1;
                    stats.fieldGoalsAttempted -= 1;
                    if (change == -3) {
                        stats.threePointsMade -= 1;
                        stats.threePointsAttempted -= 1;
                    } else if (change == -1) {
                        stats.freeThrowsMade -= 1;
                        stats.freeThrowsAttempted -= 1;
                    }
                }
                break;

            // --- RZUTY NIETRAFIONE ---
            case "2p_miss": // Rzut z gry (za 2) niecelny
                stats.fieldGoalsAttempted += change; // change będzie 1 lub -1
                break;
            case "3p_miss": // Rzut za 3 niecelny
                stats.fieldGoalsAttempted += change;
                stats.threePointsAttempted += change;
                break;
            case "ft_miss": // Rzut wolny niecelny
                stats.freeThrowsAttempted += change;
                break;

            // --- POZOSTAŁE STATYSTYKI ---
            case "rebounds":
                if (stats.rebounds + change >= 0) stats.rebounds += change;
                break;
            case "assists":
                if (stats.assists + change >= 0) stats.assists += change;
                break;
            case "steals":
                if (stats.steals + change >= 0) stats.steals += change;
                break;
            case "blocks":
                if (stats.blocks + change >= 0) stats.blocks += change;
                break;
            case "turnovers":
                if (stats.turnovers + change >= 0) stats.turnovers += change;
                break;
            case "fouls":
                int newFouls = stats.fouls + change;
                if (newFouls >= 0 && newFouls <= 5) {
                    stats.fouls = newFouls;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown stat: " + statName);
        }

        gameStatsRepository.save(stats);
        gameRepository.save(game);
    }
}