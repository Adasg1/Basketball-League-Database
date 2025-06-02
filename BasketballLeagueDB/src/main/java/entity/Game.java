package entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "SEASON_ID", nullable = false)
    public Season season;

    @ManyToOne
    @JoinColumn(name = "HOME_TEAM_ID", nullable = false)
    public Team homeTeam;

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
}