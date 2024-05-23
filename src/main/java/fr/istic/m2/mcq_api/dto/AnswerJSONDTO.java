package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Question;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AnswerJSONDTO {
    //private Long id = 0L;
    private  String title;
    private int nbrPoint = 0;
    private boolean isValid = false;
    private boolean isActive = true;

    public static AnswerJSONDTO format(Answer answer){
        AnswerJSONDTO dto = new AnswerJSONDTO();
        dto.setTitle(answer.getTitle());
        dto.setActive(answer.isActive());
        dto.setNbrPoint(answer.getNbrPoint());
        dto.setValid(answer.isValid());
        return  dto;
    }

    public static Answer formatToAnswer(AnswerJSONDTO a, Answer answer) {
        if(answer==null)
            answer = new Answer();
        answer.setTitle(a.getTitle());
        answer.setActive(a.isActive());
        answer.setNbrPoint(a.getNbrPoint());
        answer.setValid(a.isValid());

        return  answer;
    }
}
