package fr.istic.m2.mcq_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * The class to represent the User entity (A User can be a Student or a Teacher)
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uuid;
    private String firstName;
    private String lastName;
    private LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime updatedDate;
}
