package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TEAM")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Integer id;

    @Column(name = "TEAM_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "CITY", length = 32)
    private String city;

    @Column(name = "ARENA", length = 64)
    private String arena;

    @Column(name = "IS_ACTIVE", length = 1)
    private String isActive; // Można też: boolean + konwersja (opiszę niżej)

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
}
