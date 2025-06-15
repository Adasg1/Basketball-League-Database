package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "GAME_STATS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"GAME_ID", "PLAYER_ID"})
        }
)
@Check(constraints =
        "FIELD_GOALS_MADE <= FIELD_GOALS_ATTEMPTED AND " +
        "THREE_POINTS_MADE <= THREE_POINTS_ATTEMPTED AND " +
        "FREE_THROWS_MADE <= FREE_THROWS_ATTEMPTED"
)
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

    @Min(0)
    @NotNull
    @Column(name = "TIME_PLAYED", nullable = false)
    public Integer timePlayedInSeconds = 0;

    @Min(0)
    @NotNull
    @Column(name = "POINTS", nullable = false)
    public Integer points = 0;

    @Min(0)
    @NotNull
    @Column(name = "REBOUNDS", nullable = false)
    public Integer rebounds = 0;

    @Min(0)
    @NotNull
    @Column(name = "ASSISTS", nullable = false)
    public Integer assists = 0;

    @Min(0)
    @NotNull
    @Column(name = "STEALS", nullable = false)
    public Integer steals = 0;

    @Min(0)
    @NotNull
    @Column(name = "BLOCKS", nullable = false)
    public Integer blocks = 0;

    @Min(0)
    @NotNull
    @Column(name = "TURNOVERS", nullable = false)
    public Integer turnovers = 0;

    @Min(0)
    @NotNull
    @Column(name = "FIELD_GOALS_MADE", nullable = false)
    public Integer fieldGoalsMade = 0;

    @Min(0)
    @NotNull
    @Column(name = "FIELD_GOALS_ATTEMPTED", nullable = false)
    public Integer fieldGoalsAttempted = 0;

    @Min(0)
    @NotNull
    @Column(name = "THREE_POINTS_MADE", nullable = false)
    public Integer threePointsMade = 0;

    @Min(0)
    @NotNull
    @Column(name = "THREE_POINTS_ATTEMPTED", nullable = false)
    public Integer threePointsAttempted = 0;

    @Min(0)
    @NotNull
    @Column(name = "FREE_THROWS_MADE", nullable = false)
    public Integer freeThrowsMade = 0;

    @Min(0)
    @NotNull
    @Column(name = "FREE_THROWS_ATTEMPTED", nullable = false)
    public Integer freeThrowsAttempted = 0;

    @Min(0)
    @Max(5)
    @NotNull
    @Column(name = "FOULS", nullable = false)
    public Integer fouls = 0;

    public GameStats() {
    }

    public GameStats(Game game, Player player, Team team, Integer timePlayedInSeconds, Integer points,
                     Integer rebounds, Integer assists, Integer steals, Integer blocks, Integer turnovers,
                     Integer fieldGoalsMade, Integer fieldGoalsAttempted,
                     Integer threePointsMade, Integer threePointsAttempted,
                     Integer freeThrowsMade, Integer freeThrowsAttempted,
                     Integer fouls) {
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
    }

    public GameStats(Game game, Player player, Team team) {
        this.game = game;
        this.player = player;
        this.team = team;
    }

    @Version
    @Column(name = "VERSION")
    private Long version = 0L;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}