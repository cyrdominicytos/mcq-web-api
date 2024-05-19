package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * User data access interface
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // You can define additional methods here if needed
}
