package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.dto.AnswerDTO;
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

    public Answer update(Long id, AnswerDTO answerDTO) throws NoSuchElementException {
        Answer answer = this.answerRepository.findById(id).orElseThrow();
        Question question = this.questionRepository.findById(id).orElseThrow();
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
        answer.setNbrPoint(answerDTO.getNbrPoint());
        answer.setCreationDate(LocalDateTime.now());
        answer.setValid(answer.isValid());
        return answer;
    }
}
