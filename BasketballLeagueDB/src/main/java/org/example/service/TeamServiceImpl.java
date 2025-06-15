package org.example.service;

import entity.Player;
import entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.repository.PlayerRepository;
import org.example.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
    public void saveTeam(Team team) {
        this.teamRepository.save(team);
    }

    @Override
    @Transactional
    public void deleteTeam(Integer id) {
        this.teamRepository.deleteById(id);
    }
}