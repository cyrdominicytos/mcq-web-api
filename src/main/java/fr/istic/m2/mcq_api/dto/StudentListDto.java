package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentListDto {
    private Long id;
    private String uuid;
    private String firstName;
    private String lastName;

    private LevelListDto level;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
}
