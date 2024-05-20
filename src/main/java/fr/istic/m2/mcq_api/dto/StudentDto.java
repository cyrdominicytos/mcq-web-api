package fr.istic.m2.mcq_api.dto;

import lombok.Data;

@Data
public class StudentDto {
    private String uuid;
    private String firstName;
    private String lastName;
    private Long levelId;
}
