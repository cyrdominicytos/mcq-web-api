package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentTestAnswerListDto {
    private Long id;
    private int duration;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
    private StudentTestListDto studentTest;
    //TODO private AnswerListDto answer;
}
