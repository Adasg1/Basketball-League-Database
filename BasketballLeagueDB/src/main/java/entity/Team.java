package entity;

import jakarta.persistence.*;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.Check;

import java.util.List;

@Check(constraints = "IS_ACTIVE IN ('Y','N')")
@Entity
@Table(name = "TEAM")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq")
    @SequenceGenerator(name = "team_seq", sequenceName = "TEAM_SEQ", allocationSize = 1)
    @Column(name = "TEAM_ID")
    private Integer id;

    @NotNull
    @Column(name = "TEAM_NAME", length = 32, nullable = false)
    private String name;

    @NotNull
    @Column(name = "CITY", length = 32, nullable = false)
    private String city;

    @Column(name = "ARENA", length = 64)
    private String arena;

    @Pattern(regexp = "[YN]")
    @NotNull
    @Column(name = "IS_ACTIVE", length = 1, nullable = false)
    private String isActive;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<GameStats> gameStats;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PlayerTeam> playerTeams;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TeamRecord> teamRecords;

    public Team() {
    }

    public Team(String name, String city, String arena, String isActive) {
        this.name = name;
        this.city = city;
        this.arena = arena;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArena() {
        return arena;
    }

    public void setArena(String arena) {
        this.arena = arena;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
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