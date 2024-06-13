package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QcmRepository extends JpaRepository<Qcm, Long> {
    List<Qcm> findAllByTeacherId(Long id);
    Qcm findOneByTitle(String title);
    List<Qcm> findAllByLevelId(Long id);
    @Query("SELECT qcm FROM Qcm qcm WHERE qcm.isActive=true AND qcm.level.id = :levelId AND qcm.id IN  (SELECT q.qcm.id FROM Question q WHERE q.isActive=true AND q.qcm.level.id = :levelId)")
    List<Qcm> findAllByLevelIdAndQuestionsWithActiveTrue(@Param("levelId") Long levelId);

}
