package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.StudentTestAnswer;
import fr.istic.m2.mcq_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * StudentTestAnswer data access interface
 */
@Repository
public interface StudentTestAnswerRepository extends JpaRepository<StudentTestAnswer, Long> {
    // You can define additional methods here if needed
}
