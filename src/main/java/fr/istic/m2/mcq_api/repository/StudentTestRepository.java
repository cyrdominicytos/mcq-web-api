package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.StudentTest;
import fr.istic.m2.mcq_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * StudentTest data access interface
 */
public interface StudentTestRepository extends JpaRepository<StudentTest, Long> {
    // You can define additional methods here if needed
}
