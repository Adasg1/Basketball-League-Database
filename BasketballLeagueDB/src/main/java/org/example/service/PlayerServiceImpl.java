package org.example.service;

import entity.Player;
import entity.PlayerTeam;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.repository.PlayerRepository;
import org.example.repository.PlayerTeamRepository;
import org.example.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerTeamRepository playerTeamRepository;

    @Autowired
    private SeasonService seasonService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player getPlayerById(Integer id) {
        Optional<Player> optional = playerRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Player not found for id :: " + id);
        }
    }

    @Override
    @Transactional
    public void savePlayer(Player player) {
        boolean isNew = (player.getId() == null);
        Player savedPlayer;

        if (isNew) {
            // Nowy zawodnik – zapisujemy bezpiecznie
            savedPlayer = playerRepository.save(player);

            // Dodaj wpis do player_team
            PlayerTeam newEntry = new PlayerTeam();
            newEntry.setPlayer(savedPlayer);
            newEntry.setTeam(savedPlayer.getTeam());
            newEntry.setSeasonId(seasonService.getCurrentSeasonId());
            newEntry.setJerseyNumber(savedPlayer.getJerseyNumber());
            newEntry.setStartDate(LocalDate.now());
            playerTeamRepository.save(newEntry);

        } else {
            Player existingPlayer = playerRepository.findById(player.getId())
                    .orElseThrow(() -> new RuntimeException("Player not found with id: " + player.getId()));

            // Sprawdź zmianę drużyny
            boolean changedTeam = existingPlayer.getTeam() == null ||
                    !Objects.equals(existingPlayer.getTeam().getId(), player.getTeam().getId());

            // Zaktualizuj tylko potrzebne pola (NIE nadpisuj relacji jak gameStats!)
            existingPlayer.setFirstName(player.getFirstName());
            existingPlayer.setLastName(player.getLastName());
            existingPlayer.setIsActive(player.getIsActive());
            existingPlayer.setBirthDate(player.getBirthDate());
            existingPlayer.setHeight(player.getHeight());
            existingPlayer.setWeight(player.getWeight());
            existingPlayer.setNationality(player.getNationality());
            existingPlayer.setPosition(player.getPosition());
            existingPlayer.setJerseyNumber(player.getJerseyNumber());
            existingPlayer.setTeam(player.getTeam());

            savedPlayer = playerRepository.save(existingPlayer);

            // Jeśli zmienił drużynę – zamknij poprzedni wpis i otwórz nowy
            if (changedTeam) {
                PlayerTeam lastTeam = playerTeamRepository.findTopByPlayerIdOrderByStartDateDesc(savedPlayer.getId());
                if (lastTeam != null) {
                    lastTeam.setEndDate(LocalDate.now());
                    playerTeamRepository.save(lastTeam);
                }

                PlayerTeam newEntry = new PlayerTeam();
                newEntry.setPlayer(savedPlayer);
                newEntry.setTeam(savedPlayer.getTeam());
                newEntry.setSeasonId(seasonService.getCurrentSeasonId());
                newEntry.setJerseyNumber(savedPlayer.getJerseyNumber());
                newEntry.setStartDate(LocalDate.now());
                playerTeamRepository.save(newEntry);
            }
        }
    }
    @Override
    @Transactional
    public void deletePlayer(Integer id) {
        playerRepository.deleteById(id);
    }

    @Override
    public Player getById(Integer id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
    }
}