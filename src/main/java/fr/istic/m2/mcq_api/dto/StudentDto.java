package fr.istic.m2.mcq_api.dto;

import lombok.Data;

@Data
public class StudentDto {
    private String uuid;
    private String first_name;
    private String last_name;
    private Long level_id;
}
