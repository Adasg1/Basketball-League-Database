package spring.service;

import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.repository.PlayerStatsViewRepository;

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