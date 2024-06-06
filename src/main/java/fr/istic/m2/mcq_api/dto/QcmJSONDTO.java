package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class QcmJSONDTO {
    //id = 0 if new else id > 0
    private String title;
    private String details;
    private int complexity = 1;
    private int limitQuestion;
    private int delay;
    private boolean isActive = true;
    private boolean isRandomActive = false;
    private boolean canShowResultToStudents = false;
    //class  filiere (ex : M2 MIAGE DLIS )
    private String level;
    private LocalDateTime openStartDate = LocalDateTime.now();
    private LocalDateTime closeStartDate;
    private List<QuestionJSONDTO> questions = new ArrayList<>();

    public static QcmJSONDTO format(Qcm qcm){
        QcmJSONDTO dto = new QcmJSONDTO();
        dto.setTitle(qcm.getTitle());
        dto.setDetails(qcm.getDetails());
        dto.setDelay(qcm.getDelay());
        dto.setActive(qcm.isActive());
        dto.setRandomActive(qcm.isRandomActive());
        dto.setCanShowResultToStudents(qcm.isCanShowResultToStudents());
        dto.setOpenStartDate(qcm.getOpenStartDate());
        dto.setCloseStartDate(qcm.getCloseStartDate());
        dto.setComplexity(qcm.getComplexity());
        dto.setLimitQuestion(qcm.getLimitQuestion());
        dto.setLevel(qcm.getLevel().getClassOfStudy()+" "+qcm.getLevel().getFieldOfStudy());
        List<QuestionJSONDTO> questionsList = new ArrayList<>();
        for(Question q : qcm.getQuestions())
            questionsList.add(QuestionJSONDTO.format(q));
        dto.setQuestions(questionsList);
        return  dto;
    }
}
