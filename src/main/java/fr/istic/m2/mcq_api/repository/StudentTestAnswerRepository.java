package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.StudentTestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * StudentTestAnswer data access interface
 */
@Repository
public interface StudentTestAnswerRepository extends JpaRepository<StudentTestAnswer, Long> {
    List<StudentTestAnswer> findByAnswerIn(List<Answer> answers);
}
