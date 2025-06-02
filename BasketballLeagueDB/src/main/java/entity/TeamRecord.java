package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TEAM_RECORD")
public class TeamRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_record_seq")
    @SequenceGenerator(name = "team_record_seq", sequenceName = "TEAM_RECORD_SEQ", allocationSize = 1)
    @Column(name = "TEAM_RECORD_ID")
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = false)
    public Team team;

    @ManyToOne
    @JoinColumn(name = "SEASON_ID", nullable = false)
    public Season season;

    @Column(name = "WINS")
    public Integer wins;

    @Column(name = "LOSSES")
    public Integer losses;

    @Column(name = "HOME_WINS")
    public Integer homeWins;

    @Column(name = "HOME_LOSSES")
    public Integer homeLosses;

    @Column(name = "AWAY_WINS")
    public Integer awayWins;

    @Column(name = "AWAY_LOSSES")
    public Integer awayLosses;
}