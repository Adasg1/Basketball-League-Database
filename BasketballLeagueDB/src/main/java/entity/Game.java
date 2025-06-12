package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
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
    public Season season;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "HOME_TEAM_ID", nullable = false)
    public Team homeTeam;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "AWAY_TEAM_ID", nullable = false)
    public Team awayTeam;

    @ManyToOne
    @JoinColumn(name = "WINNER_TEAM_ID")
    public Team winnerTeam;

    @Column(name = "GAME_DATE")
    public LocalDate gameDate;

    @Column(name = "HOME_SCORE")
    public Integer homeScore;

    @Column(name = "AWAY_SCORE")
    public Integer awayScore;

    @Column(name = "STATUS", length = 1)
    public String status;

    @Column(name = "CURRENT_PERIOD")
    public Integer currentPeriod;

    @Column(name = "TIME_REMAINING")
    public Double timeRemaining;

    @OneToMany(mappedBy = "game")
    public List<GameStats> gameStats;

    public Game() {
    }

    public Game(Season season, Team homeTeam, Team awayTeam, Team winnerTeam,
                LocalDate gameDate, Integer homeScore, Integer awayScore,
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
}