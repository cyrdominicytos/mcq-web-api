package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.AnswerComment;
import fr.istic.m2.mcq_api.dto.AnswerCommentDTO;
import fr.istic.m2.mcq_api.repository.QuestionCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerCommentService {
    @Autowired
    private QuestionCommentRepository questionCommentRepository;

    public AnswerComment update(Long id, AnswerCommentDTO answerCommentDTO) {
        return new AnswerComment();
    }

    public AnswerComment create(AnswerCommentDTO answerCommentDTO) {
        return new AnswerComment();
    }

    public AnswerComment read(Long id) {
        return new AnswerComment();
    }
}
