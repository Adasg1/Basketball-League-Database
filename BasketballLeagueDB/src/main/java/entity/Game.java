package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "GAME")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq")
    @SequenceGenerator(name = "game_seq", sequenceName = "GAME_SEQ", allocationSize = 1)
    @Column(name = "GAME_ID")
    public Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "SEASON_ID", nullable = false)
    private Season season;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "HOME_TEAM_ID", nullable = false)
    private Team homeTeam;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "AWAY_TEAM_ID", nullable = false)
    private Team awayTeam;

    @ManyToOne
    @JoinColumn(name = "WINNER_TEAM_ID")
    private Team winnerTeam;

    @Column(name = "GAME_DATE")
    private LocalDateTime gameDate;

    @Min(0)
    @NotNull
    @Column(name = "HOME_SCORE", nullable = false)
    private Integer homeScore = 0;

    @Min(0)
    @NotNull
    @Column(name = "AWAY_SCORE", nullable = false)
    private Integer awayScore = 0;

    @NotNull
    @Pattern(regexp = "[SAF]") // scheduled, active, finished
    @Column(name = "STATUS", length = 1, nullable = false)
    private String status = "S";

    @Min(0)
    @NotNull
    @Column(name = "CURRENT_PERIOD", nullable = false)
    private Integer currentPeriod = 1;

    @Min(0)
    @NotNull
    @Column(name = "TIME_REMAINING", nullable = false)
    private Double timeRemaining = 600.0;

    @OneToMany(mappedBy = "game")
    private List<GameStats> gameStats;

    public Game() {
    }

    public Game(Season season, Team homeTeam, Team awayTeam, Team winnerTeam,
                LocalDateTime gameDate, Integer homeScore, Integer awayScore,
                String status, Integer currentPeriod, Double timeRemaining) {
        this.season = season;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.winnerTeam = winnerTeam;
        this.gameDate = gameDate;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = status;
        this.currentPeriod = currentPeriod;
        this.timeRemaining = timeRemaining;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Season getSeason() { return season; }
    public void setSeason(Season season) { this.season = season; }

    public Team getHomeTeam() { return homeTeam; }
    public void setHomeTeam(Team homeTeam) { this.homeTeam = homeTeam; }

    public Team getAwayTeam() { return awayTeam; }
    public void setAwayTeam(Team awayTeam) { this.awayTeam = awayTeam; }

    public Team getWinnerTeam() { return winnerTeam; }
    public void setWinnerTeam(Team winnerTeam) { this.winnerTeam = winnerTeam; }

    public LocalDateTime getGameDate() { return gameDate; }
    public void setGameDate(LocalDateTime gameDate) { this.gameDate = gameDate; }

    public Integer getHomeScore() { return homeScore; }
    public void setHomeScore(Integer homeScore) { this.homeScore = homeScore; }

    public Integer getAwayScore() { return awayScore; }
    public void setAwayScore(Integer awayScore) { this.awayScore = awayScore; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getCurrentPeriod() { return currentPeriod; }
    public void setCurrentPeriod(Integer currentPeriod) { this.currentPeriod = currentPeriod; }

    public Double getTimeRemaining() { return timeRemaining; }
    public void setTimeRemaining(Double timeRemaining) { this.timeRemaining = timeRemaining; }

    public List<GameStats> getGameStats() { return gameStats; }
    public void setGameStats(List<GameStats> gameStats) { this.gameStats = gameStats; }


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