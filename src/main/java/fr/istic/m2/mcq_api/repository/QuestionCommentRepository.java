package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.AnswerComment;
import fr.istic.m2.mcq_api.domain.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {
    List<QuestionComment> findByQuestionId(Long id);
}
