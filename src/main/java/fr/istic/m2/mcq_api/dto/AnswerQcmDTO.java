package fr.istic.m2.mcq_api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnswerQcmDTO {
    Long studentId;
    List<QuestionAnswerDTO> answers = new ArrayList<>();
    List<QuestionCommentDTO> questionsComments = new ArrayList<>();
    List<AnswerCommentDTO> answersComments = new ArrayList<>();
}
