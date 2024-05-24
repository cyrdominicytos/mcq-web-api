package fr.istic.m2.mcq_api.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
public class QuestionComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    private Question question;
    private String suggestion;
    private String description;
    private boolean isAccepted = false;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;


    @JsonGetter("questionId")
    public Long commentQuestionId(){
        return this.question.getId();
    }
}
