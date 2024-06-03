package fr.istic.m2.mcq_api.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.istic.m2.mcq_api.dto.LevelDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Qcm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    private Level level;
    @ManyToOne
    @JsonIgnore
    private Teacher teacher;

    @OneToMany(mappedBy = "qcm",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Question> questions  = new ArrayList<>();;

    @OneToMany(mappedBy = "qcm", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<StudentTest> studentTestList = new ArrayList<>();

    private int limitQuestion;
    private boolean isActive;
    private int delay;
    private String title;
    private int complexity;
    private boolean isRandomActive = false;
    private LocalDateTime openStartDate;
    private LocalDateTime closeStartDate;
    private LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime updatedDate;


    @JsonGetter("teacherId")
    public Long getQcmTeacher(){
        return this.teacher.getId();
    }

    @JsonGetter("level")
    public Map<String,  Object> qcmLevel() {
        return (new LevelDto()).toMap(this.level);
    }
}
