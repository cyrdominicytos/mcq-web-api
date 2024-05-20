package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Qcm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Level level;
    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "qcm",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Question> questions;

    @OneToMany(mappedBy = "qcm", cascade = CascadeType.ALL)
    private List<StudentTest> studentTestList = new ArrayList<>();

    private int limitQuestion;
    private boolean isActive;
    private int delay;
    private String title;
    private int complexity;
    private boolean isRandomActive = false;
    private LocalDateTime openStartDate;
    private LocalDateTime closeStartDate;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
}
