package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.dto.AnswerDTO;
import fr.istic.m2.mcq_api.dto.AnswerListDTO;
import fr.istic.m2.mcq_api.dto.QcmListDTO;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.AnswerRepository;
import fr.istic.m2.mcq_api.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    public Answer read(Long id) {
        return this.answerRepository.findById(id).orElse(null);
    }

    public Answer create(AnswerDTO answerDTO) {
        Question question = this.questionRepository
                                .findById(answerDTO.getQuestionId())
                                .get();
        Answer answer = this.formatAnswer(answerDTO, question);
        this.answerRepository.saveAndFlush(answer);
        return answer;
    }

    public Answer update(Long id, AnswerDTO answerDTO) throws ResourceNotFoundException {
        Answer answer = this.answerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Answer", "id", id));
        Question question = this.questionRepository.findById(answerDTO.getQuestionId()).orElseThrow(()-> new ResourceNotFoundException("Question", "id", answerDTO.getQuestionId()));
        answer.setValid(answerDTO.isValid());
        answer.setNbrPoint(answerDTO.getNbrPoint());
        answer.setQuestion(question);
        answer.setUpdatedDate(LocalDateTime.now());
        return answer;
    }

    public void delete(Long id) {
        this.answerRepository.deleteById(id);
    }

    public Answer formatAnswer(AnswerDTO answerDTO, Question question){
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setTitle(answerDTO.getTitle());
        answer.setActive(answerDTO.isActive());
        answer.setNbrPoint(answerDTO.getNbrPoint());
        answer.setCreationDate(LocalDateTime.now());
        answer.setValid(answer.isValid());
        return answer;
    }

    public static AnswerListDTO convertToListDto(Answer source){
        AnswerListDTO result = new AnswerListDTO();
        result.setId(source.getId());
        result.setActive(source.isActive());
        result.setTitle(source.getTitle());
        result.setValid(source.isValid());
        result.setNbrPoint(source.getNbrPoint());
        result.setQuestionId(source.getQuestion().getId());
        result.setCreationDate(source.getCreationDate());
        result.setUpdatedDate(source.getUpdatedDate());
        result.setCommentCount(source.getComments().size());
        result.setStudentTestAnswerCount(source.getStudentTestAnswers().size());
        return result;
    }
}
