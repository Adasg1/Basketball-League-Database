package entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PLAYER_TEAMS")
public class PlayerTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_team_seq")
    @SequenceGenerator(name = "player_team_seq", sequenceName = "PLAYER_TEAM_SEQ", allocationSize = 1)
    @Column(name = "PLAYER_TEAM_ID")
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "PLAYER_ID", nullable = false)
    public Player player;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = false)
    public Team team;

    @Column(name = "SEASON_ID")
    public Integer seasonId;

    @Column(name = "JERSEY_NUMBER")
    public Integer jerseyNumber;

    @Column(name = "START_DATE")
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
}