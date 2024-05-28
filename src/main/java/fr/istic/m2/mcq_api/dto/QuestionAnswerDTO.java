package fr.istic.m2.mcq_api.dto;

import lombok.Data;

@Data
public class QuestionAnswerDTO {
    private Long questionId;
    private Long answerId;
    private int duration;
}
