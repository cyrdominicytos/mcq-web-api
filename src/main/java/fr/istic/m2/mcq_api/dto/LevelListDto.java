package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LevelListDto {
    private Long id;
    private String fieldOfStudy;
    private String classOfStudy;
    private int studentCount;
    private int qcmCount;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
}
