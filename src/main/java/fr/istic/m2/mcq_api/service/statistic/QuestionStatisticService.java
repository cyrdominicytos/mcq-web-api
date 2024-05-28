package fr.istic.m2.mcq_api.service.statistic;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.domain.StudentTestAnswer;
import fr.istic.m2.mcq_api.dto.AnswerStatDTO;
import fr.istic.m2.mcq_api.repository.QcmRepository;
import fr.istic.m2.mcq_api.repository.StudentTestAnswerRepository;
import fr.istic.m2.mcq_api.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionStatisticService {
    private final QcmRepository qcmRepository;
    private final QuestionService questionService;
    private final StudentTestAnswerRepository testAnswerRepository;

    @Autowired
    QuestionStatisticService(
            QcmRepository qcmRepository,
            QuestionService questionService,
            StudentTestAnswerRepository testAnswerRepository
    ){
        this.qcmRepository = qcmRepository;
        this.questionService = questionService;
        this.testAnswerRepository = testAnswerRepository;
    }

    public Object getQcmStat(Long id){
        return this.getQuestionStats(id);
    }

    public List<AnswerStatDTO> getQuestionStats(Long id){
        Question question = questionService.read(id);
        List<Answer> answers = question.getAnswers();
        List< StudentTestAnswer> studentTestAnswers = this.testAnswerRepository.findByAnswerIn(answers);
        Integer totalAnswer = studentTestAnswers.size();

        if (totalAnswer == 0){
            return null;
        }
        List<AnswerStatDTO> answerStatDTOList = new ArrayList<>();
        for(Answer answer : answers){
            Integer answeredTotal = studentTestAnswers.stream()
                            .filter(s -> s.getId() == answer.getId())
                            .toList()
                            .size();
            AnswerStatDTO stat = new AnswerStatDTO();
            stat.setAnswerId(answer.getId());
            stat.setPercent(answeredTotal/totalAnswer);
        }

        return answerStatDTOList;
    }
}
