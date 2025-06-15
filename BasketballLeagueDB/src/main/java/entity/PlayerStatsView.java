package entity;

import entity.id.PlayerStatsViewId;
import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@IdClass(PlayerStatsViewId.class)
@Table(name = "PLAYER_STATS_BY_SEASON_VIEW")
public class PlayerStatsView {

    @Id
    @Column(name = "PLAYER_ID")
    private Integer playerId;

    @Id
    @Column(name = "SEASON_ID")
    private Integer seasonId;

    @Id
    @Column(name = "TEAM_ID")
    private Integer teamId;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "TEAM_NAME")
    private String teamName;

    @Column(name = "GAMES_PLAYED")
    private Integer gamesPlayed;

    @Column(name = "TOTAL_POINTS")
    private Integer totalPoints;

    @Column(name = "TOTAL_REBOUNDS")
    private Integer totalRebounds;

    @Column(name = "TOTAL_ASSISTS")
    private Integer totalAssists;

    @Column(name = "TOTAL_STEALS")
    private Integer totalSteals;

    @Column(name = "TOTAL_BLOCKS")
    private Integer totalBlocks;

    @Column(name = "TOTAL_FGA")
    private Integer totalFga;

    @Column(name = "TOTAL_FGM")
    private Integer totalFgm;

    @Column(name = "TOTAL_FTA")
    private Integer totalFta;

    @Column(name = "TOTAL_FTM")
    private Integer totalFtm;

    @Column(name = "TOTAL_TURNOVERS")
    private Integer totalTurnovers;

    @Column(name = "TOTAL_3PA")
    private Integer total3pa;

    @Column(name = "TOTAL_3PM")
    private Integer total3pm;

    @Column(name = "AVG_POINTS")
    private Double avgPoints;

    @Column(name = "AVG_REBOUNDS")
    private Double avgRebounds;

    @Column(name = "AVG_ASSISTS")
    private Double avgAssists;

    @Column(name = "AVG_EVAL")
    private Double avgEval;

    public Integer getPlayerId()       { return playerId; }
    public Integer getSeasonId()       { return seasonId; }
    public Integer getTeamId()         { return teamId; }
    public String getFirstName()       { return firstName; }
    public String getLastName()        { return lastName; }
    public String getTeamName()        { return teamName; }
    public Integer getGamesPlayed()    { return gamesPlayed; }
    public Integer getTotalPoints()    { return totalPoints; }
    public Integer getTotalRebounds()  { return totalRebounds; }
    public Integer getTotalAssists()   { return totalAssists; }
    public Integer getTotalSteals()    { return totalSteals; }
    public Integer getTotalBlocks()    { return totalBlocks; }
    public Integer getTotalFga()       { return totalFga; }
    public Integer getTotalFgm()       { return totalFgm; }
    public Integer getTotalFta()       { return totalFta; }
    public Integer getTotalFtm()       { return totalFtm; }
    public Integer getTotalTurnovers() { return totalTurnovers; }
    public Integer getTotal3pa()       { return total3pa; }
    public Integer getTotal3pm()       { return total3pm; }
    public Double getAvgPoints()       { return avgPoints; }
    public Double getAvgRebounds()     { return avgRebounds; }
    public Double getAvgAssists()      { return avgAssists; }
    public Double getAvgEval()         { return avgEval; }
}