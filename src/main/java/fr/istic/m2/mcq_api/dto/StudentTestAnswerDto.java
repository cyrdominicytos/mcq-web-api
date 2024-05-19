package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.StudentTest;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentTestAnswerDto {
    private int duration;
    private Long student_test_id;
    private Long answer_id;
}
