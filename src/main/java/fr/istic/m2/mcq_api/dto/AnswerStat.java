package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Answer;
import lombok.Data;

@Data
public class AnswerStat {
    private Answer answer;
    private AnswerStatDTO answerStatDTO;
}
