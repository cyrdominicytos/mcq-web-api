package fr.istic.m2.mcq_api.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class StudentTestListDto {
    private Long id;
    private LocalDateTime startingDate;
    private LocalDateTime endDate;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;

    private StudentListDto student;
    private QcmListDTO qcm;

}
