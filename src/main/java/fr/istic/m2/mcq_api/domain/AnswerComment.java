package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class AnswerComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Answer answer;
    private String suggestion;
    private String description;
    private boolean isAccepted = false;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
}
