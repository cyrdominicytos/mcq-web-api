package fr.istic.m2.mcq_api.dto;

import lombok.Data;

@Data
public class AnswerCommentDTO {
    private Long answerId;
    private String suggestion;
    private String description;
    private boolean isAccepted = false;
}
