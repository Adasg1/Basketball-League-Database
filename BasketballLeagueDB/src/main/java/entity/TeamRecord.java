package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Check;

@Check(constraints = "WINS + LOSSES = HOME_WINS + HOME_LOSSES + AWAY_WINS + AWAY_LOSSES")
@Entity
@Table(name = "TEAM_RECORD",
        uniqueConstraints = @UniqueConstraint(columnNames = {"TEAM_ID", "SEASON_ID"}))
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

    @Min(0)
    @NotNull
    @Column(name = "WINS", nullable = false)
    public Integer wins = 0;

    @Min(0)
    @NotNull
    @Column(name = "LOSSES", nullable = false)
    public Integer losses = 0;

    @Min(0)
    @NotNull
    @Column(name = "HOME_WINS", nullable = false)
    public Integer homeWins = 0;

    @Min(0)
    @NotNull
    @Column(name = "HOME_LOSSES", nullable = false)
    public Integer homeLosses = 0;

    @Min(0)
    @NotNull
    @Column(name = "AWAY_WINS", nullable = false)
    public Integer awayWins = 0;

    @Min(0)
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
    public void incrementWins() { this.wins++; }

    public void incrementLosses() { this.losses++; }

    public void incrementHomeWins() { this.homeWins++; }

    public void incrementHomeLosses() { this.homeLosses++; }

    public void incrementAwayWins() { this.awayWins++; }

    public void incrementAwayLosses() { this.awayLosses++; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
    public Season getSeason() { return season; }
    public void setSeason(Season season) { this.season = season; }
    public Integer getWins() { return wins; }
    public void setWins(Integer wins) { this.wins = wins; }
    public Integer getLosses() { return losses; }
    public void setLosses(Integer losses) { this.losses = losses; }
    public Integer getHomeWins() { return homeWins; }
    public void setHomeWins(Integer homeWins) { this.homeWins = homeWins; }
    public Integer getHomeLosses() { return homeLosses; }
    public void setHomeLosses(Integer homeLosses) { this.homeLosses = homeLosses; }
    public Integer getAwayWins() { return awayWins; }
    public void setAwayWins(Integer awayWins) { this.awayWins = awayWins; }
    public Integer getAwayLosses() { return awayLosses; }
    public void setAwayLosses(Integer awayLosses) { this.awayLosses = awayLosses; }



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