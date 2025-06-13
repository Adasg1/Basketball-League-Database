package entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

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

    @NotNull
    @Past
    @Column(name = "BIRTHDATE", nullable = false)
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

    @Pattern(regexp = "[YN]")
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
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.position = position;
        this.nationality = nationality;
        this.height = height;
        this.weight = weight;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getJersey_number() {
        return jersey_number;
    }

    public void setJersey_number(Integer jersey_number) {
        this.jersey_number = jersey_number;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public List<PlayerTeam> getPlayerTeams() {
        return playerTeams;
    }

    public void setPlayerTeams(List<PlayerTeam> playerTeams) {
        this.playerTeams = playerTeams;
    }

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