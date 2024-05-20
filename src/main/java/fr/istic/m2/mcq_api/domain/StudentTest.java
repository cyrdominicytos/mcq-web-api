package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * The class representing the completion of a MCQ test by a student
 */
@Data
@Entity
public class StudentTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startingDate;
    private LocalDateTime endDate;
    private LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime updatedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qcm_id")
    private Qcm qcm;

    @OneToMany(mappedBy = "studentTest", cascade = CascadeType.ALL)
    private List<StudentTestAnswer> studentTestAnswer = new ArrayList<>();

}
