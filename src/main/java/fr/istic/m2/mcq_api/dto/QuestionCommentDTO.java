package fr.istic.m2.mcq_api.dto;

import lombok.Data;

@Data
public class QuestionCommentDTO {
    private Long questionId;
    private String suggestion;
    private String description;
    private boolean isAccepted = false;
}
