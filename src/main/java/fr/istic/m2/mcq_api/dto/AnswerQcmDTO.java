package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnswerQcmDTO {
    Long studentId;
    Long qcmId;
    List<QuestionAnswerDTO> answers = new ArrayList<>();
}
