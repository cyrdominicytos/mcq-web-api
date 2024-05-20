package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeacherListDto {
    private Long id;
    private String uuid;
    private String firstName;
    private String lastName;
    private int qcmCount;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
}
