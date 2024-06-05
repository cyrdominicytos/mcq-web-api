package fr.istic.m2.mcq_api.service.statistic;

import fr.istic.m2.mcq_api.domain.*;
import fr.istic.m2.mcq_api.dto.AnswerStatDTO;
import fr.istic.m2.mcq_api.dto.UnAnswerQuestionDTO;
import fr.istic.m2.mcq_api.repository.QcmRepository;
import fr.istic.m2.mcq_api.repository.StudentTestAnswerRepository;
import fr.istic.m2.mcq_api.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        if (answers.size() == 0){
            return null;
        }
        List< StudentTestAnswer> studentTestAnswers = this.testAnswerRepository.findByAnswerIn(answers);
        Integer totalAnswer = studentTestAnswers.size();
        if (totalAnswer == 0){
            return null;
        }
        List<AnswerStatDTO> answerStatDTOList = new ArrayList<>();
        for(Answer answer : answers){
            Integer answeredTotal = studentTestAnswers.stream()
                            .filter(s -> s.getAnswer().getId() == answer.getId())
                            .toList()
                            .size();
            AnswerStatDTO stat = new AnswerStatDTO();
            stat.setAnswerId(answer.getId());
            stat.setPercent((float) Math.ceil((float) answeredTotal)/totalAnswer * 100);
            answerStatDTOList.add(stat);
        }
        return answerStatDTOList;
    }

    public List<UnAnswerQuestionDTO> unAnswerQuestionsStats(Qcm qcm){
        List<Long> studentsIds =  qcm.getStudentTestList()
                .stream()
                .map(s -> s.getId())
                .toList();
        List<UnAnswerQuestionDTO> answerQuestionDTOList = new ArrayList<>();
        qcm.getQuestions().forEach(q -> answerQuestionDTOList.add(this.unAnswerCount(q, studentsIds)));
        return answerQuestionDTOList;
    }


    public UnAnswerQuestionDTO unAnswerCount(Question question, List<Long> studentsIds){
        UnAnswerQuestionDTO answerQuestionDTO = new UnAnswerQuestionDTO();
        Set<Long> answerStudentIds =  new HashSet<>();
        List<Answer> answers = question.getAnswers();
        for (Answer answer : answers){
            answer.getStudentTestAnswers()
                    .forEach(s -> answerStudentIds.add(s.getId()));
        }
        Integer count = 0;
        for (Long id : studentsIds){
            if (!answers.contains(id)) count++;
        }
        answerQuestionDTO.setCount(count);
        answerQuestionDTO.setQuestionId(question.getId());
        return answerQuestionDTO;
    }
}
