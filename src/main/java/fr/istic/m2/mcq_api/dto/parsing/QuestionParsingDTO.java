package fr.istic.m2.mcq_api.dto.parsing;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
@Entity
public class QuestionParsingDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Option> options = new ArrayList<>();
    @ElementCollection
    private Map<String, String> meta = new HashMap<>();*/
}