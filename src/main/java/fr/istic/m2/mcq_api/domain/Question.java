package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer level;
    private boolean isActive;
    private Integer delay;
    private LocalDateTime  creationDate;
    private LocalDateTime  updatedDate;
}
