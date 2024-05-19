package fr.istic.m2.mcq_api.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    private int qcmId;
    private int levelId;
    private boolean isActive;
    private int delay;
    private String title;
}
