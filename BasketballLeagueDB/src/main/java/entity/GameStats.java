package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "GAME_STATS")
public class GameStats {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_stats_seq")
    @SequenceGenerator(name = "game_stats_seq", sequenceName = "GAME_STATS_SEQ", allocationSize = 1)
    @Column(name = "STAT_ID")
    public Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "GAME_ID", nullable = false)
    public Game game;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PLAYER_ID", nullable = false)
    public Player player;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = false)
    public Team team;

    @NotNull
    @Column(name = "TIME_PLAYED", nullable = false)
    public Integer timePlayedInSeconds = 0;

    @NotNull
    @Column(name = "POINTS", nullable = false)
    public Integer points = 0;

    @NotNull
    @Column(name = "REBOUNDS", nullable = false)
    public Integer rebounds = 0;

    @NotNull
    @Column(name = "ASSISTS", nullable = false)
    public Integer assists = 0;

    @NotNull
    @Column(name = "STEALS", nullable = false)
    public Integer steals = 0;

    @NotNull
    @Column(name = "BLOCKS", nullable = false)
    public Integer blocks = 0;

    @NotNull
    @Column(name = "TURNOVERS", nullable = false)
    public Integer turnovers = 0;

    @NotNull
    @Column(name = "FIELD_GOALS_MADE", nullable = false)
    public Integer fieldGoalsMade = 0;

    @NotNull
    @Column(name = "FIELD_GOALS_ATTEMPTED", nullable = false)
    public Integer fieldGoalsAttempted = 0;

    @NotNull
    @Column(name = "THREE_POINTS_MADE", nullable = false)
    public Integer threePointsMade = 0;

    @NotNull
    @Column(name = "THREE_POINTS_ATTEMPTED", nullable = false)
    public Integer threePointsAttempted = 0;

    @NotNull
    @Column(name = "FREE_THROWS_MADE", nullable = false)
    public Integer freeThrowsMade = 0;

    @NotNull
    @Column(name = "FREE_THROWS_ATTEMPTED", nullable = false)
    public Integer freeThrowsAttempted = 0;

    @NotNull
    @Column(name = "FOULS", nullable = false)
    public Integer fouls = 0;

    @NotNull
    @Column(name = "FOULS_ON", nullable = false)
    public Integer foulsOn = 0;

    @NotNull
    @Column(name = "PLUS_MINUS", nullable = false)
    public Integer plusMinus = 0;

    public GameStats() {
    }

    public GameStats(Game game, Player player, Team team, Integer timePlayedInSeconds, Integer points,
                     Integer rebounds, Integer assists, Integer steals, Integer blocks, Integer turnovers,
                     Integer fieldGoalsMade, Integer fieldGoalsAttempted,
                     Integer threePointsMade, Integer threePointsAttempted,
                     Integer freeThrowsMade, Integer freeThrowsAttempted,
                     Integer fouls, Integer foulsOn, Integer plusMinus) {
        this.game = game;
        this.player = player;
        this.team = team;
        this.timePlayedInSeconds = timePlayedInSeconds;
        this.points = points;
        this.rebounds = rebounds;
        this.assists = assists;
        this.steals = steals;
        this.blocks = blocks;
        this.turnovers = turnovers;
        this.fieldGoalsMade = fieldGoalsMade;
        this.fieldGoalsAttempted = fieldGoalsAttempted;
        this.threePointsMade = threePointsMade;
        this.threePointsAttempted = threePointsAttempted;
        this.freeThrowsMade = freeThrowsMade;
        this.freeThrowsAttempted = freeThrowsAttempted;
        this.fouls = fouls;
        this.foulsOn = foulsOn;
        this.plusMinus = plusMinus;
    }
}