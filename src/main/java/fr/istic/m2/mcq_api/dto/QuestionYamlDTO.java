package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class QuestionYamlDTO {
        private String title;
        private boolean active = true;
        private int delay = 0;
        private int complexity = 0;
        private List<AnswerYamlDTO> answers;


// Getters and Setters

        @Override
        public String toString() {
            return "Question{" +
                    "title='" + title + '\'' +
                    ", isActive=" + active +
                    ", delay=" + delay +
                    ", complexity=" + complexity +
                    ", answers=" + answers +
                    '}';
        }

        public static List<Answer>  formatQuestion(QuestionYamlDTO question, Question dto){

                if(dto==null)
                        dto = new Question();
                dto.setTitle(question.getTitle());
                dto.setDelay(question.getDelay());
                dto.setActive(question.isActive());
                dto.setComplexity(question.getComplexity());
                List<Answer> answers = new ArrayList<>();
                for(AnswerYamlDTO a : question.getAnswers())
                {
                        Answer answer = new Answer();
                        answer.setQuestion(dto);
                        answers.add(AnswerYamlDTO.formatAnswer(a, answer));
                }
                return  answers;
        }
}
