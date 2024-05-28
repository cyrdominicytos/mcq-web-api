package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
