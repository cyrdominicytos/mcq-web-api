package fr.istic.m2.mcq_api.repository;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}