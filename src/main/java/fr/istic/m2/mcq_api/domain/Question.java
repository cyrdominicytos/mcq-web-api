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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionComment> comments = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private Qcm qcm;
    private String title;
    private boolean isActive;
    private int delay;
    private int complexity;
    private LocalDateTime  creationDate;
    private LocalDateTime  updatedDate;


    @JsonGetter("qcmId")
    public Long getQuestionQcmId(){
        return this.qcm.getId();
    }
}
