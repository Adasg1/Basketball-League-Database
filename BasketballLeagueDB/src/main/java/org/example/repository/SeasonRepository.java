package org.example.repository;

import entity.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {
    Optional<Season> findTopByOrderBySeasonYearDesc();
}