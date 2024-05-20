package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();
    @OneToMany(mappedBy = "question")
    private List<QuestionComment> comments = new ArrayList<>();
    @ManyToOne
    private Qcm qcm;
    @ManyToOne
    private Level level;
    private String title;
    private boolean isActive;
    private int delay;
    private LocalDateTime  creationDate;
    private LocalDateTime  updatedDate;
}
