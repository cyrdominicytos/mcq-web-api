package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class TestAnswer {
    //TODO : should be removed And need to revert related dev. TestAnswer = StudentTestAnswer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Answer answer;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
}
