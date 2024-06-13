package fr.istic.m2.mcq_api.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QcmAllStatDTO {
    private Qcm qcm;
    private QcmStatDTO qcmStatDTO;
    private List<QuestionStatDTO> questionsStats = new ArrayList<>();
}
