package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class QcmEditYamlDTO {
    private List<QuestionEditYamlDTO> questions;
    @Override
    public String toString() {
        return "Qcm{" +
                "questions=" + questions +
                '}';
    }
}
