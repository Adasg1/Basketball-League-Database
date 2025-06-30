package spring.service;

import entity.GameStats;
import entity.Player;
import entity.Team;
import spring.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team getTeamById(Integer id) {
        Optional<Team> optional = teamRepository.findById(id);
        Team team = null;
        if (optional.isPresent()) {
            team = optional.get();
        } else {
            throw new RuntimeException(" Team not found for id :: " + id);
        }
        return team;
    }

    @Override
    @Transactional
    public void saveTeam(Team incomingTeam) {
        if (incomingTeam.getId() != null) {
            Team existingTeam = teamRepository.findById(incomingTeam.getId())
                    .orElseThrow(() -> new RuntimeException("Team not found"));

            existingTeam.setName(incomingTeam.getName());
            existingTeam.setCity(incomingTeam.getCity());
            existingTeam.setArena(incomingTeam.getArena());
            existingTeam.setIsActive(incomingTeam.getIsActive());

            existingTeam.getGameStats().clear();
            if (incomingTeam.getGameStats() != null) {
                for (GameStats gs : incomingTeam.getGameStats()) {
                    gs.setTeam(existingTeam);
                    existingTeam.getGameStats().add(gs);
                }
            }

            teamRepository.save(existingTeam);
        } else {
            if (incomingTeam.getGameStats() != null) {
                for (GameStats gs : incomingTeam.getGameStats()) {
                    gs.setTeam(incomingTeam);
                }
            }
            teamRepository.save(incomingTeam);
        }
    }

    @Override
    @Transactional
    public void deleteTeam(Integer id) {
        this.teamRepository.deleteById(id);
    }
}