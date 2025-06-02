package entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "PLAYER")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq")
    @SequenceGenerator(name = "player_seq", sequenceName = "PLAYER_SEQ", allocationSize = 1)
    @Column(name = "PLAYER_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    public Team team;

    @Column(name = "FIRSTNAME", length = 32)
    public String firstName;

    @Column(name = "LASTNAME", length = 32)
    public String lastName;

    @Column(name = "BIRTHDATE")
    public LocalDate birthDate;

    @Column(name = "POSITION", length = 32)
    public String position;

    @Column(name = "NATIONALITY", length = 32)
    public String nationality;

    @Column(name = "JERSEY_NUMBER")
    public Integer jersey_number;

    @Column(name = "HEIGHT")
    public Integer height;

    @Column(name = "WEIGHT")
    public Integer weight;

    @Column(name = "IS_ACTIVE", length = 1)
    public String isActive;

    @OneToMany(mappedBy = "player")
    public List<PlayerTeam> playerTeams;
}