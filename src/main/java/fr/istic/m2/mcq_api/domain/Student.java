package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * The class to represent the Student entity
 */
@Data
@Entity
public class Student extends User {
    private String lastFieldOfStudy;
    private String lastClassOfStudy;

    @OneToMany(mappedBy = "student")
    List<StudentTest> studentTestList = new ArrayList<>();
}
