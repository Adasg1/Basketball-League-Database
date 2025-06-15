package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "SEASON")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "season_seq")
    @SequenceGenerator(name = "season_seq", sequenceName = "SEASON_SEQ", allocationSize = 1)
    @Column(name = "SEASON_ID")
    public Integer id;

    @NotNull
    @Column(name = "SEASON_YEAR", nullable = false)
    private Integer seasonYear;

    @NotNull
    @Column(name = "START_DATE", nullable = false, unique = true)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @OneToMany(mappedBy = "season", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public List<TeamRecord> teamRecords;

    @OneToMany(mappedBy = "season", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Game> games;


    public Season() {
    }

    public Season(Integer seasonYear, LocalDate startDate, LocalDate endDate) {
        this.seasonYear = seasonYear;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(Integer seasonYear) {
        this.seasonYear = seasonYear;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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