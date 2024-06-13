package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Level;
import fr.istic.m2.mcq_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Level data access interface
 */
@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    public Level findOneByClassOfStudyAndFieldOfStudy(String classOfStudy, String fieldOfStudy);
}
