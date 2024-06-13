package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionJSONDTO {
    private String title;
    private int complexity = 0;
    private boolean isActive = true;
    private int delay = 0;
    List<AnswerJSONDTO> answers = new ArrayList<>();
    public static QuestionJSONDTO format(Question question){
        QuestionJSONDTO dto = new QuestionJSONDTO();
        dto.setTitle(question.getTitle());
        dto.setDelay(question.getDelay());
        dto.setActive(question.isActive());
        dto.setComplexity(question.getComplexity());
        List<AnswerJSONDTO> answerList = new ArrayList<>();
        for(Answer a : question.getAnswers())
            answerList.add(AnswerJSONDTO.format(a));
        dto.setAnswers(answerList);
        return  dto;
    }
    public static List<Answer>  formatToQuestion(QuestionJSONDTO question, Question dto){

        if(dto==null)
             dto = new Question();
        dto.setTitle(question.getTitle());
        dto.setDelay(question.getDelay());
        dto.setActive(question.isActive());
        dto.setComplexity(question.getComplexity());
        List<Answer> answers = new ArrayList<>();
        for(AnswerJSONDTO a : question.getAnswers())
        {
            Answer answer = new Answer();
            answer.setQuestion(dto);
            answers.add(AnswerJSONDTO.formatToAnswer(a, answer));
        }
        return  answers;
    }
}
