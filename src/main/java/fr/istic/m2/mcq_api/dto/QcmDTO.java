package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QcmDTO {
    private Long levelId;
    private Long teacherId;
    private int limitQuestion;
    private int delay;
    private String title;
    private int complexity;
    private boolean isRandomActive = false;
    private LocalDateTime openStartDate; // Timestamp or LocalDateTime ? Which do you prefer ?
    private LocalDateTime closeStartDate;
}
