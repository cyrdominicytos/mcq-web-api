package fr.istic.m2.mcq_api.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private int questionId;
    private boolean isValid;
    private int nbrPoint;
}
