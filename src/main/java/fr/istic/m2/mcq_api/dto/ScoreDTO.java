package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Score;
import lombok.Data;

@Data
public class ScoreDTO {
    private Long qcmId;
    private Long studentId;
    private Integer totalValidQuestion;
    private Integer totalQuestion;

    public static ScoreDTO toScoreDTO(Score score){
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setQcmId(score.getQcm().getId());
        scoreDTO.setStudentId(score.getStudent().getId());
        scoreDTO.setTotalValidQuestion(score.getTotalValidQuestion());
        scoreDTO.setTotalQuestion(score.getTotalQuestion());
        return scoreDTO;
    }
}
