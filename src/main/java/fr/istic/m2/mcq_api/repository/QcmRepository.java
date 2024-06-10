package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QcmRepository extends JpaRepository<Qcm, Long> {
    List<Qcm> findAllByTeacherId(Long id);
    Qcm findOneByTitle(String title);
    List<Qcm> findAllByLevelId(Long id);
}
