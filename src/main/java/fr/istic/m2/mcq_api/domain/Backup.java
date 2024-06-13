package fr.istic.m2.mcq_api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Backup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @OneToOne
    private Teacher teacher;
    private LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime updatedDate;
}
