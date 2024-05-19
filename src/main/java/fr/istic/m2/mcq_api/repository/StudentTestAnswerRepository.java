package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.StudentTestAnswer;
import fr.istic.m2.mcq_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * StudentTestAnswer data access interface
 */
public interface StudentTestAnswerRepository extends JpaRepository<StudentTestAnswer, Long> {
    // You can define additional methods here if needed
}
