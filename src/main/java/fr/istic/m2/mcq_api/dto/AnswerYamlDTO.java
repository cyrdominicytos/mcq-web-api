package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Answer;
import lombok.Data;

@Data
public class AnswerYamlDTO {
    private String title;
    private boolean active;
    private boolean valid;

    @Override
    public String toString() {
        return "Answers{" +
                "title='" + title + '\'' +
                ", isActive=" + active +
                ", isValid=" + valid +
                '}';
    }

    public static Answer formatAnswer(AnswerYamlDTO a, Answer answer) {
        if(answer==null)
            answer = new Answer();
        answer.setTitle(a.getTitle());
        answer.setActive(a.isActive());
        answer.setNbrPoint(0);
        answer.setValid(a.isValid());
        return  answer;
    }
}
