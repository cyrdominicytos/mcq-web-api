package fr.istic.m2.mcq_api.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * The class that represents a student's response to a specific test
 */
@Data
@Entity
public class StudentTestAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int duration;
    private LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime updatedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_test_id")
    private StudentTest studentTest;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private Answer answer;

}
