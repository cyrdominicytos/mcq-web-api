package fr.istic.m2.mcq_api.dto;

import java.time.LocalDateTime;

public class QcmDTO {
    private int levelId;
    private int limitQuestion;
    private int delay;
    private LocalDateTime openStartDate; // Timestamp or LocalDateTime ? Which do you prefer ?
    private LocalDateTime closeStartDate;
}
