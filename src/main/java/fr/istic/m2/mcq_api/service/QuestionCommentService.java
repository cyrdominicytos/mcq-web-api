package fr.istic.m2.mcq_api.service;


import fr.istic.m2.mcq_api.domain.QuestionComment;
import fr.istic.m2.mcq_api.dto.QuestionCommentDTO;
import fr.istic.m2.mcq_api.repository.QuestionCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionCommentService {
    @Autowired
    private QuestionCommentRepository questionCommentRepository;

    public QuestionComment read(Long id) {
        return new QuestionComment();
    }

    public QuestionComment create(QuestionCommentDTO questionCommentDTO) {
        return new QuestionComment();
    }

    public QuestionComment update(Long id, QuestionCommentDTO questionCommentDTO) {
        return new QuestionComment();
    }

    public void delete(Long id) {
    }
}
