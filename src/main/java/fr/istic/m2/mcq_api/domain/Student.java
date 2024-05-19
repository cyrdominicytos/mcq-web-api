package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.*;
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
    @OneToMany(mappedBy = "student")
    List<StudentTest> studentTestList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "level_id")
    Level studentLevel;
}
