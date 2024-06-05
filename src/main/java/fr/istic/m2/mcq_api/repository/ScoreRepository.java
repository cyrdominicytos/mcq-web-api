package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Query("SELECT  MAX((u.totalValidQuestion * 100/u.totalQuestion)) FROM Score u WHERE u.qcm.id = :id")
    Double getTheHighScoreByQcmId(@Param("id") Long id);
    @Query("SELECT  MIN((u.totalValidQuestion * 100/u.totalQuestion)) FROM Score u WHERE u.qcm.id = :id")
    Double getMinScoreByQcmId(@Param("id") Long id);
    @Query("SELECT  AVG((u.totalValidQuestion * 100/u.totalQuestion)) FROM Score u WHERE u.qcm.id = :id")
    Double getAverageScoreByQcmId(@Param("id") Long id);
}
