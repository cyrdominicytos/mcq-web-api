package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeacherListDto {
    private Long id;
    private String uuid;
    private String first_name;
    private String last_name;

    //TODO : private List<QcmListDto> qcm_list;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
}
