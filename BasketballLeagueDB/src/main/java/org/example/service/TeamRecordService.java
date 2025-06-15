package org.example.service;

import entity.TeamRecord;

import java.util.List;

public interface TeamRecordService {
    void updateRecordsAfterGame(Integer gameId);
    List<TeamRecord> getRecordsBySeasonIdOrderByWinsDesc(Integer seasonId);
}