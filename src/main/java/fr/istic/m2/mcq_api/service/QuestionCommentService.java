package fr.istic.m2.mcq_api.service;


import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.AnswerComment;
import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.domain.QuestionComment;
import fr.istic.m2.mcq_api.dto.QuestionCommentDTO;
import fr.istic.m2.mcq_api.repository.QuestionCommentRepository;
import fr.istic.m2.mcq_api.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class QuestionCommentService {
    @Autowired
    private QuestionCommentRepository questionCommentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public QuestionComment read(Long id) {
        return this.questionCommentRepository.findById(id).orElse(null);
    }

    public QuestionComment create(QuestionCommentDTO questionCommentDTO) {
        try {
            Question question = this.questionRepository.findById(questionCommentDTO.getQuestionId()).orElseThrow();
            QuestionComment comment = this.formatComment(questionCommentDTO, question);
            this.questionCommentRepository.saveAndFlush(comment);
            return comment;
        }catch (NoSuchElementException e){
            return null;
        }
    }

    private QuestionComment formatComment(QuestionCommentDTO questionCommentDTO, Question question) {
        QuestionComment c = new QuestionComment();
        c.setQuestion(question);
        c.setAccepted(questionCommentDTO.isAccepted());
        c.setSuggestion(questionCommentDTO.getSuggestion());
        c.setUpdatedDate(LocalDateTime.now());
        c.setCreationDate(LocalDateTime.now());
        return c;
    }

    public QuestionComment update(Long id, QuestionCommentDTO questionCommentDTO) {
        try {
            QuestionComment comment = this.questionCommentRepository.findById(id).orElseThrow();
            comment.setSuggestion(questionCommentDTO.getSuggestion());
            comment.setAccepted(questionCommentDTO.isAccepted());
            comment.setUpdatedDate(LocalDateTime.now());
            this.questionCommentRepository.saveAndFlush(comment);
            return comment;
        }catch (NoSuchElementException e){
            return null;
        }
    }

    public void delete(Long id) {
        this.questionCommentRepository.deleteById(id);
    }
}
