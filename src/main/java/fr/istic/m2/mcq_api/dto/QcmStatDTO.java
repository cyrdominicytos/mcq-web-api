package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QcmStatDTO {
    private Double highScore;
    private Double averageScore;
    private Double minScore;

    List<UnAnswerQuestionDTO> unAnswerQuestions = new ArrayList<>();
}
