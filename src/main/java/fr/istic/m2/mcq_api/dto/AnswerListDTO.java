package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerListDTO {
    private Long id;
    private Long questionId;
    private boolean isValid;
    private int nbrPoint;
    private  int commentCount;
    private  int studentTestAnswerCount;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
}
