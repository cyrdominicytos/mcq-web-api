package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * The class, to represent a class and its sector
 */
@Data
@Entity
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fieldOfStudy;
    private String classOfStudy;
    private LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime updatedDate;

    /*@OneToMany(mappedBy = "level")
    List<Qcm> qcmList = new ArrayList<>();*/
}
