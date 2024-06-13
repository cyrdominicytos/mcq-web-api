package fr.istic.m2.mcq_api.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    private Long qcmId;
    private int complexity = 0;
    private boolean isActive = true;
    private int delay = 0;
    private String title;
}
