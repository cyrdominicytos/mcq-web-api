package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Qcm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Level level;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Question> questions;
    private int limitQuestion;
    private boolean isActive;
    private int delay;
    private LocalDateTime openStartDate;
    private LocalDateTime closeStartDate;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
}
