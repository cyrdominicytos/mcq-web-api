package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Level;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.dto.QuestionDTO;
import fr.istic.m2.mcq_api.repository.LevelRepository;
import fr.istic.m2.mcq_api.repository.QcmRepository;
import fr.istic.m2.mcq_api.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class QuestionService {
    @Autowired
    private QcmRepository qcmRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private QuestionRepository questionRepository;
    public Question read(Long id) throws NoSuchElementException{
        return this.questionRepository.findById(id).orElseThrow();
    }

    public Question create(QuestionDTO questionDTO) {
        Question question = this.formatQuestion(questionDTO, null);
        this.questionRepository.saveAndFlush(question);
        return question;
    }

    public Question update(Long id, QuestionDTO questionDTO) throws NoSuchElementException {
        Question question = this.questionRepository.findById(id).orElseThrow();
        question = this.formatQuestion(questionDTO, question);
        this.questionRepository.saveAndFlush(question);
        return question;
    }

    public void delete(Long id) {
        this.questionRepository.deleteById(id);
    }

    public Question formatQuestion(QuestionDTO questionDTO, Question question) throws NoSuchElementException {
        Qcm qcm = this.qcmRepository.findById(questionDTO.getQcmId()).orElseThrow();
        //Level level = this.levelRepository.findById(questionDTO.getLevelId()).orElseThrow();
        if (question == null){
            question = new Question();
            question.setCreationDate(LocalDateTime.now());
        }
        question.setQcm(qcm);
        question.setTitle(questionDTO.getTitle());
        question.setActive(question.isActive());
        question.setDelay(questionDTO.getDelay());
        question.setComplexity(questionDTO.getComplexity());
        question.setUpdatedDate(LocalDateTime.now());
        return question;
    }


}