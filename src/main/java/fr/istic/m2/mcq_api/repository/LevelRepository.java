package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Level;
import fr.istic.m2.mcq_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Level data access interface
 */
public interface LevelRepository extends JpaRepository<Level, Long> {
    // You can define additional methods here if needed
}
