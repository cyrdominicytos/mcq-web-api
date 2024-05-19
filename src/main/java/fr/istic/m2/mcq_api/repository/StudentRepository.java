package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Student;
import fr.istic.m2.mcq_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Student data access interface
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    // You can define additional methods here if needed
}
