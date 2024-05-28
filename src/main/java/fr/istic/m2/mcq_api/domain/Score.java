package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Qcm qcm;
    @ManyToOne
    private Student student;
    private Integer totalValidQuestion;
    private Integer totalQuestion;
}
