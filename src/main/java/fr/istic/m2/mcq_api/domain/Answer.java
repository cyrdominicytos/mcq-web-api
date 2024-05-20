package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "answer", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<AnswerComment> comments;
    @ManyToOne
    private Question question;
    @OneToMany(mappedBy = "answer", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private List<TestAnswer> testAnswers = new ArrayList<>();
    private boolean isValid;
    private int nbrPoint;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
}
