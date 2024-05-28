package fr.istic.m2.mcq_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class QcmYamlDTO {
    private List<QuestionYamlDTO> questions;
    @Override
    public String toString() {
        return "Qcm{" +
                "questions=" + questions +
                '}';
    }
}
