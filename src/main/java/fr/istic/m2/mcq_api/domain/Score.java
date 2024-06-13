package fr.istic.m2.mcq_api.domain;

import fr.istic.m2.mcq_api.dto.ScoreDTO;
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
