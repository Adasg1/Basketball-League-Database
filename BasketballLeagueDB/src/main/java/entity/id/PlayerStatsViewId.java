package entity.id;

import java.io.Serializable;
import java.util.Objects;

public class PlayerStatsViewId implements Serializable {

    private Integer playerId;
    private Integer seasonId;
    private Integer teamId;

    public PlayerStatsViewId() {
    }

    public PlayerStatsViewId(Integer playerId, Integer seasonId, Integer teamId) {
        this.playerId = playerId;
        this.seasonId = seasonId;
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerStatsViewId)) return false;
        PlayerStatsViewId that = (PlayerStatsViewId) o;
        return Objects.equals(playerId, that.playerId) &&
                Objects.equals(seasonId, that.seasonId) &&
                Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, seasonId, teamId);
    }
}