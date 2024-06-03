package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Answer;
import lombok.Data;

@Data
public class AnswerEditYamlDTO {

    private String title;
    private boolean valid = false;
    private boolean active = true;
    private Long answerId;


    @Override
    public String toString() {
        return "Answers{" +
                "answserId='" + answerId + '\'' +
                "title='" + title + '\'' +
                ", isActive=" + active +
                ", isValid=" + valid +
                '}';
    }

    public static Answer formatAnswer(AnswerEditYamlDTO a, Answer answer) {
        if(answer==null)
            answer = new Answer();
        answer.setTitle(a.getTitle());
        answer.setActive(a.isActive());
        answer.setNbrPoint(0);
        answer.setValid(a.isValid());
        return  answer;
    }
}
