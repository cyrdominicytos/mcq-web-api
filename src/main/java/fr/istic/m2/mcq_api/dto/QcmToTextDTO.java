package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Qcm;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QcmToTextDTO {
    private Long id;
    private int limitQuestion;
    private int delay;
    private boolean isActive;
    private String title;
    private String details;
    private int complexity;
    private boolean isRandomActive = false;
    private boolean canShowResultToStudents = false;
    private LocalDateTime openStartDate;
    private LocalDateTime closeStartDate;
    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
    private int testCount;
    private int questionCount;
    private Long levelId;
    private Long teacherId;

    private  String content = "";

    public static  QcmToTextDTO formatQCMToQcmToTextDTO(Qcm qcm, String text){
        QcmToTextDTO dto = new QcmToTextDTO();
        dto.setId(qcm.getId());
        dto.setTitle(qcm.getTitle());
        dto.setDetails(qcm.getDetails());
        dto.setActive(qcm.isActive());
        dto.setCanShowResultToStudents(qcm.isCanShowResultToStudents());
        dto.setComplexity(qcm.getComplexity());
        dto.setCloseStartDate(qcm.getCloseStartDate());
        dto.setOpenStartDate(qcm.getOpenStartDate());
        dto.setLimitQuestion(qcm.getLimitQuestion());
        dto.setDelay(qcm.getDelay());
        dto.setRandomActive(qcm.isRandomActive());
        dto.setTestCount(qcm.getStudentTestList().size());
        dto.setQuestionCount(qcm.getQuestions().size());
        dto.setCreationDate(qcm.getCreationDate());
        dto.setUpdatedDate(qcm.getUpdatedDate());
        dto.setContent(text);
        dto.setLevelId(qcm.getLevel().getId());
        dto.setTeacherId(qcm.getTeacher().getId());

        return dto;
    }
}
