package entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

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

    @NotNull
    @Column(name = "FIRSTNAME", length = 32, nullable = false)
    public String firstName;

    @NotNull
    @Column(name = "LASTNAME", length = 32, nullable = false)
    public String lastName;

    @Past
    @Column(name = "BIRTHDATE")
    public LocalDate birthDate;

    @NotNull
    @Column(name = "POSITION", length = 16, nullable = false)
    public String position;

    @NotNull
    @Column(name = "NATIONALITY", length = 32, nullable = false)
    public String nationality;

    @Column(name = "JERSEY_NUMBER")
    public Integer jersey_number;

    @NotNull
    @Column(name = "HEIGHT", nullable = false)
    public Integer height;

    @NotNull
    @Column(name = "WEIGHT", nullable = false)
    public Integer weight;

    @NotNull
    @Column(name = "IS_ACTIVE", length = 1, nullable = false)
    public String isActive;

    @OneToMany(mappedBy = "player")
    public List<PlayerTeam> playerTeams;

    public Player() {
    }

    public Player(Team team, String firstName, String lastName, LocalDate birthDate,
                  String position, String nationality, Integer jerseyNumber,
                  Integer height, Integer weight, String isActive) {
        this.team = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.position = position;
        this.nationality = nationality;
        this.jersey_number = jerseyNumber;
        this.height = height;
        this.weight = weight;
        this.isActive = isActive;
    }
}