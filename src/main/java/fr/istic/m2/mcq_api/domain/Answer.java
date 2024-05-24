package fr.istic.m2.mcq_api.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private List<AnswerComment> comments  = new ArrayList<>();;

    @ManyToOne
    @JsonIgnore
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private List<StudentTestAnswer> studentTestAnswers = new ArrayList<>();

    private boolean isValid = false;
    private boolean isActive = true;
    private int nbrPoint;
    private String title;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;


    @JsonGetter("questionId")
    public Long getAnswerQuestionId(){
        return this.question.getId();
    }
}
