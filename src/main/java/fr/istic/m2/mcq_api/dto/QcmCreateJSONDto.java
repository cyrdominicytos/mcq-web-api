package fr.istic.m2.mcq_api.dto;

import lombok.Data;

@Data
public class QcmCreateJSONDto {
    private Long teacherId;
    private Long levelId;
    private Long qcmId;
}
