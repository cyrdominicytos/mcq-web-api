package fr.istic.m2.mcq_api.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private  String title;
    private int nbrPoint;
    private Long questionId;
    private boolean isValid = false;
    private boolean isActive = true;
}
