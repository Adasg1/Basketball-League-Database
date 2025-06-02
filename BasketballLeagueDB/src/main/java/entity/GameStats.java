package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "GAME_STATS")
public class GameStats {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_stats_seq")
    @SequenceGenerator(name = "game_stats_seq", sequenceName = "GAME_STATS_SEQ", allocationSize = 1)
    @Column(name = "STAT_ID")
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "GAME_ID", nullable = false)
    public Game game;

    @ManyToOne
    @JoinColumn(name = "PLAYER_ID", nullable = false)
    public Player player;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = false)
    public Team team;

    @Column(name = "TIME_PLAYED")
    public Integer timePlayedInSeconds;

    @Column(name = "POINTS")
    public Integer points;

    @Column(name = "REBOUNDS")
    public Integer rebounds;

    @Column(name = "ASSISTS")
    public Integer assists;

    @Column(name = "STEALS")
    public Integer steals;

    @Column(name = "BLOCKS")
    public Integer blocks;

    @Column(name = "TURNOVERS")
    public Integer turnovers;

    @Column(name = "FIELD_GOALS_MADE")
    public Integer fieldGoalsMade;

    @Column(name = "FIELD_GOALS_ATTEMPTED")
    public Integer fieldGoalsAttempted;

    @Column(name = "THREE_POINTS_MADE")
    public Integer threePointsMade;

    @Column(name = "THREE_POINTS_ATTEMPTED")
    public Integer threePointsAttempted;

    @Column(name = "FREE_THROWS_MADE")
    public Integer freeThrowsMade;

    @Column(name = "FREE_THROWS_ATTEMPTED")
    public Integer freeThrowsAttempted;

    @Column(name = "FOULS")
    public Integer fouls;

    @Column(name = "FOULS_ON")
    public Integer foulsOn;

    @Column(name = "PLUS_MINUS")
    public Integer plusMinus;
}