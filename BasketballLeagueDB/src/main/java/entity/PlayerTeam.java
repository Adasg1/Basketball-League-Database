package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "PLAYER_TEAMS")
public class PlayerTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_team_seq")
    @SequenceGenerator(name = "player_team_seq", sequenceName = "PLAYER_TEAM_SEQ", allocationSize = 1)
    @Column(name = "PLAYER_TEAM_ID")
    public Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PLAYER_ID", nullable = false)
    public Player player;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = false)
    public Team team;

    @NotNull
    @Column(name = "SEASON_ID", nullable = false)
    public Integer seasonId;

    @NotNull
    @Column(name = "JERSEY_NUMBER", nullable = false)
    public Integer jerseyNumber;

    @NotNull
    @Column(name = "START_DATE", nullable = false)
    public LocalDate startDate;

    @Column(name = "END_DATE")
    public LocalDate endDate;

    public PlayerTeam() {
    }

    public PlayerTeam(Player player, Team team, Integer seasonId, Integer jerseyNumber, LocalDate startDate, LocalDate endDate) {
        this.player = player;
        this.team = team;
        this.seasonId = seasonId;
        this.jerseyNumber = jerseyNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getId() { return id; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
    public Integer getJerseyNumber() { return jerseyNumber; }
    public void setJerseyNumber(Integer jerseyNumber) { this.jerseyNumber = jerseyNumber; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Integer getSeasonId() { return seasonId; }
    public void setSeasonId(Integer seasonId) { this.seasonId = seasonId; }

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