package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QcmWithTextDTO {
    private String text ;
    private QcmDTO dto;
}
