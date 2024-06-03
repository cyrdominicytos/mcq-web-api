package fr.istic.m2.mcq_api.service;


import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.domain.QuestionComment;
import fr.istic.m2.mcq_api.dto.QuestionCommentDTO;
import fr.istic.m2.mcq_api.repository.QuestionCommentRepository;
import fr.istic.m2.mcq_api.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public List<QuestionComment> createAll(List<QuestionCommentDTO> questionsCommentsDTO){
        List<QuestionComment> comments = new ArrayList<>();
        questionsCommentsDTO.forEach(i -> comments.add(this.create(i)));
        return comments;
    }

    private QuestionComment formatComment(QuestionCommentDTO questionCommentDTO, Question question) {
        QuestionComment c = new QuestionComment();
        c.setQuestion(question);
        c.setAccepted(questionCommentDTO.isAccepted());
        c.setDescription(questionCommentDTO.getDescription());
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

    public List<QuestionComment> getAllByQuestionId(Long id) {
        return this.questionCommentRepository.findByQuestionId(id);
    }
}
