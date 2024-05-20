package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * The class to represent the teacher entity
 */
@Data
@Entity
public class Teacher extends User {
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    List<Qcm> qcmList = new ArrayList<>();

}
