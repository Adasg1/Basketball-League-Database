package org.example.service;

import entity.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerStatsViewServiceImpl implements PlayerStatsViewService {

    @Autowired
    private PlayerStatsViewRepository repository;

    @Override
    public List<PlayerStatsView> getByPlayerId(Integer playerId) {
        return repository.findByPlayerId(playerId);
    }
}