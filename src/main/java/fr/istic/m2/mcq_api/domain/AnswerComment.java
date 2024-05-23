package fr.istic.m2.mcq_api.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Answer answer;
    private String suggestion;
    private String description;
    private boolean isAccepted = false;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;

    @JsonGetter("answerId")
    public Long getCommentAnswerId(){
        return this.getAnswer().getId();
    }
}
