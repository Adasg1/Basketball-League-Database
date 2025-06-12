package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "TEAM_RECORD")
public class TeamRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_record_seq")
    @SequenceGenerator(name = "team_record_seq", sequenceName = "TEAM_RECORD_SEQ", allocationSize = 1)
    @Column(name = "TEAM_RECORD_ID")
    public Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = false)
    public Team team;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "SEASON_ID", nullable = false)
    public Season season;

    @NotNull
    @Column(name = "WINS", nullable = false)
    public Integer wins = 0;

    @NotNull
    @Column(name = "LOSSES", nullable = false)
    public Integer losses = 0;

    @NotNull
    @Column(name = "HOME_WINS", nullable = false)
    public Integer homeWins = 0;

    @NotNull
    @Column(name = "HOME_LOSSES", nullable = false)
    public Integer homeLosses = 0;

    @NotNull
    @Column(name = "AWAY_WINS", nullable = false)
    public Integer awayWins = 0;

    @NotNull
    @Column(name = "AWAY_LOSSES", nullable = false)
    public Integer awayLosses = 0;

    public TeamRecord() {
    }

    public TeamRecord(Team team, Season season, Integer wins, Integer losses,
                      Integer homeWins, Integer homeLosses,
                      Integer awayWins, Integer awayLosses) {
        this.team = team;
        this.season = season;
        this.wins = wins;
        this.losses = losses;
        this.homeWins = homeWins;
        this.homeLosses = homeLosses;
        this.awayWins = awayWins;
        this.awayLosses = awayLosses;
    }
}