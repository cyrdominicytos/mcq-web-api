package fr.istic.m2.mcq_api.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.istic.m2.mcq_api.dto.LevelDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

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

    @OneToMany(mappedBy = "qcm", cascade = CascadeType.ALL)
    private List<Question> questions  = new ArrayList<>();

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

    public Question removeQuestionById(Long questionId){
        Question removedQuestion = null;
        System.out.println("========== Questions size before size="+questions.size()+"==========");
        for(Question q : questions)
            if(Objects.equals(q.getId(), questionId))
            {
                 this.questions.remove(q);
                 q.setQcm(null);
                 removedQuestion = q;
                System.out.println("========== Find question with title="+q.getTitle()+" id="+q.getId());
                System.out.println("========== Find question with title="+q.getTitle()+" id="+q.getId());

            }
        System.out.println("========== Questions size after size="+questions.size()+"==========");
    return removedQuestion;
    }
}
