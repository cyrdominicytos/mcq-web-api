package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.AnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerCommentRepository extends JpaRepository<AnswerComment, Long> {
}
