package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerListDTO {
    private Long id;
    private  String title;
    private int nbrPoint;
    private Long questionId;
    private boolean isValid = false;
    private boolean isActive = true;
    private  int commentCount;
    private  int studentTestAnswerCount;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
}
