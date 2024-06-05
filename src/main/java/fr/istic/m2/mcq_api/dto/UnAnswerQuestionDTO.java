package fr.istic.m2.mcq_api.dto;

import lombok.Data;

@Data
public class UnAnswerQuestionDTO {
    Long questionId;
    Integer count;
}
