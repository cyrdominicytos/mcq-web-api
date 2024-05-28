package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.AnswerComment;
import fr.istic.m2.mcq_api.dto.AnswerCommentDTO;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.AnswerCommentRepository;
import fr.istic.m2.mcq_api.repository.AnswerRepository;
import fr.istic.m2.mcq_api.repository.QuestionCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AnswerCommentService {
    @Autowired
    private
    AnswerCommentRepository answerCommentRepository;
    @Autowired
    private AnswerRepository answerRepository;

    public AnswerComment update(Long id, AnswerCommentDTO answerCommentDTO) {
        try {
            AnswerComment comment = this.answerCommentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("AnswerComment", "id", id));
            comment.setSuggestion(answerCommentDTO.getSuggestion());
            comment.setDescription(answerCommentDTO.getDescription());
            comment.setAccepted(answerCommentDTO.isAccepted());
            comment.setUpdatedDate(LocalDateTime.now());
            this.answerCommentRepository.saveAndFlush(comment);
            return comment;
        }catch (NoSuchElementException e){
            return null;
        }
    }

    public AnswerComment create(AnswerCommentDTO answerCommentDTO) {
        try {
            Answer answer = this.getAnswer(answerCommentDTO.getAnswerId());
            AnswerComment comment = this.formatComment(answerCommentDTO, answer);
            this.answerCommentRepository.saveAndFlush(comment);
            return comment;
        }catch (NoSuchElementException e){
            return null;
        }
    }

    public AnswerComment read(Long id) {
        Optional<AnswerComment> commentOptional = this.answerCommentRepository.findById(id);
        if (commentOptional.isEmpty()){
            return null;
        }
        return commentOptional.get();
    }

    private AnswerComment formatComment(AnswerCommentDTO answerCommentDTO, Answer answer){
        AnswerComment c = new AnswerComment();
        c.setAnswer(answer);
        c.setAccepted(answerCommentDTO.isAccepted());
        c.setSuggestion(answerCommentDTO.getSuggestion());
        c.setDescription(answerCommentDTO.getDescription());
        c.setUpdatedDate(LocalDateTime.now());
        c.setCreationDate(LocalDateTime.now());
        return c;
    }

    public Answer getAnswer(Long id) throws ResourceNotFoundException {
        return this.answerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Answer", "id", id));
    }

    public void delete(Long id) {
        this.answerCommentRepository.deleteById(id);
    }

    public List<AnswerComment> getAllByAnswerId(Long id) {
        return this.answerCommentRepository.findByAnswerId(id);
    }

    public List<AnswerComment> getAll() {
        return this.answerCommentRepository.findAll();
    }
}
