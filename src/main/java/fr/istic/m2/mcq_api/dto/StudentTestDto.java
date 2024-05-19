package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Student;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentTestDto {
    private LocalDateTime startingDate;
    private LocalDateTime endDate;
    private Long student_id;
    private Long qcm_id;
}
