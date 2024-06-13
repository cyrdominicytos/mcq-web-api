package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionStatDTO {
    private Question question;
    private UnAnswerQuestionDTO unAnswerQuestionDTO;
    private List<AnswerStat> answerStats = new ArrayList<>();
}
