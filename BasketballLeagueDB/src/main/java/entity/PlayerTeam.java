package entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PLAYER_TEAMS")
public class PlayerTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYER_TEAM_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PLAYER_ID")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Column(name = "SEASON_ID")
    private Integer seasonId;

    @Column(name = "JERSEY_NUMBER")
    private Integer jerseyNumber;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

}