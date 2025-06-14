package org.example.service;

import entity.Season;
import entity.TeamRecord;

import java.util.List;

public interface TeamRecordService {
    void updateRecordsAfterGame(Integer gameId);
    List<TeamRecord> getAllTeamRecords();
    List<TeamRecord> getRecordsBySeasonId(Integer seasonId);
}