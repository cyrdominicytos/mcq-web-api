package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QcmListDTO {
    private Long id;
    private int limitQuestion;
    private int delay;
    private boolean isActive;
    private String title;
    private int complexity;
    private boolean isRandomActive = false;
    private LocalDateTime openStartDate;
    private LocalDateTime closeStartDate;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;

    private int testCount;
    private int questionCount;
    private TeacherListDto teacher;
}
