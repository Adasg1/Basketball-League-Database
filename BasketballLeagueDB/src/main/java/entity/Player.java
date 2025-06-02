package entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "PLAYER")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYER_ID")
    private Integer id;

    @Column(name = "FIRSTNAME", length = 32)
    private String firstname;

    @Column(name = "LASTNAME", length = 32)
    private String lastname;

    @Column(name = "BIRTHDATE")
    private LocalDate birthdate;

    @Column(name = "POSITION", length = 32)
    private String position;

    @Column(name = "NATIONALITY", length = 32)
    private String nationality;

    @Column(name = "JERSEYNUMBER")
    private Integer jerseynumber;

    @Column(name = "HEIGHT")
    private Integer height;

    @Column(name = "WEIGHT")
    private Integer weight;

    @Column(name = "IS_ACTIVE", length = 1)
    private String isActive;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<PlayerTeam> playerTeams;

    // gettery/settery
}