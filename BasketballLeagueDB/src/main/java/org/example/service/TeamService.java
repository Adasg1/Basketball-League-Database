package org.example.service;

import entity.Team;
import java.util.List;

public interface TeamService {
    List<Team> getAllTeams();
    Team getTeamById(Integer id);
    void saveTeam(Team team);
    void deleteTeam(Integer id);
}