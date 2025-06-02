package entity;

import jakarta.persistence.*;
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

    @Column(name = "SEASON_YEAR", length = 16)
    public String seasonYear;

    @Column(name = "START_DATE")
    public LocalDate startDate;

    @Column(name = "END_DATE")
    public LocalDate endDate;

    @OneToMany(mappedBy = "season")
    public List<TeamRecord> teamRecords;

    public Season() {
    }

    public Season(String seasonYear, LocalDate startDate, LocalDate endDate) {
        this.seasonYear = seasonYear;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}