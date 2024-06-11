package fr.istic.m2.mcq_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.istic.m2.mcq_api.domain.Teacher;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class BackupDto {
    @Lob
    private String content;
    private Long teacherId;

}
