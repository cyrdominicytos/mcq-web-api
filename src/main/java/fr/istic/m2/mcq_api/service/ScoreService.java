package fr.istic.m2.mcq_api.service;


import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.dto.AnswerQcmDTO;
import fr.istic.m2.mcq_api.dto.QcmStatDTO;
import fr.istic.m2.mcq_api.dto.QuestionAnswerDTO;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.AnswerRepository;
import fr.istic.m2.mcq_api.repository.QcmRepository;
import fr.istic.m2.mcq_api.repository.ScoreRepository;
import fr.istic.m2.mcq_api.service.statistic.QuestionStatisticService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreService {

    private final QcmRepository qcmRepository;
    private final AnswerRepository answerRepository;
    private final ScoreRepository scoreRepository;
    private final QuestionStatisticService questionStatisticService;

    public ScoreService(QcmRepository qcmRepository,
                        AnswerRepository answerRepository,
                        ScoreRepository scoreRepository,
                        QuestionStatisticService questionStatisticService
                        ){
        this.qcmRepository = qcmRepository;
        this.answerRepository = answerRepository;
        this.scoreRepository = scoreRepository;
        this.questionStatisticService = questionStatisticService;
    }

    public Integer getQcmTotalValidAnswer(Long qcmId){
        Qcm qcm = this.qcmRepository.findById(qcmId).orElseThrow(() -> new ResourceNotFoundException(String.format("qcmid: %d not found", qcmId)));
        Integer total = 0;
        List<Question> questions = qcm.getQuestions();
        for(Question question: questions){
            List<Answer> answers = question.getAnswers();
            for (Answer answer : answers){
                if (answer.isValid()){
                    total++;
                }
            }
        }
        return total;
    }

    public Integer getStudentTotalValidAnswer(AnswerQcmDTO answerQcmDTO){
        Integer total = 0;
        List<QuestionAnswerDTO> answers = answerQcmDTO.getAnswers();
        List<Long> questionIds = new ArrayList<>();
        for (QuestionAnswerDTO questionAnswerDTO : answers){
            Long questionId = questionAnswerDTO.getQuestionId();
            if (!questionIds.contains(questionId)){
              total += this.getQuestionTotal(
                      answers.stream()
                              .filter(s -> s.getQuestionId() == questionId)
                              .toList()
              );
              questionIds.add(questionId);
            }
        }
        return total;
    }

    /**
     * @param answers
     * @return 1 answer is correct of 0 when there is an incorrect answer
     */

    private Integer getQuestionTotal(List<QuestionAnswerDTO> answers){
        for (QuestionAnswerDTO answerDTO : answers){
            Answer answer = this.answerRepository.findById(answerDTO.getAnswerId()).get();
            if (!answer.isValid()){
                return 0;
            }
        }
        return 1;
    }

    public QcmStatDTO getQcmStats(Long id){
        Qcm qcm = this.qcmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("qcmid=%d not found", id)));
        QcmStatDTO statDTO = new QcmStatDTO();
        statDTO.setHighScore(this.scoreRepository.getTheHighScoreByQcmId(qcm.getId()));
        statDTO.setMinScore(this.scoreRepository.getMinScoreByQcmId(qcm.getId()));
        statDTO.setAverageScore(scoreRepository.getAverageScoreByQcmId(qcm.getId()));
        statDTO.setUnAnswerQuestions(this.questionStatisticService.unAnswerQuestionsStats(qcm));
        return statDTO;
    }

}
