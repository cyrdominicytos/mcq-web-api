package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionEditYamlDTO {

        private String title;
        private int delay = 0;
        private int complexity = 1;
        private boolean active = true;
        private Long questionId = 0L;
        private List<AnswerEditYamlDTO> answers;


// Getters and Setters

        @Override
        public String toString() {
            return "Question{" +
                    "questionId='" + questionId + '\'' +
                    "title='" + title + '\'' +
                    ", isActive=" + active +
                    ", delay=" + delay +
                    ", complexity=" + complexity +
                    ", answers=" + answers +
                    '}';
        }

        public static List<Answer>  formatQuestion(QuestionEditYamlDTO question, Question dto){

                if(dto==null)
                        dto = new Question();
                dto.setTitle(question.getTitle());
                dto.setDelay(question.getDelay());
                dto.setActive(question.isActive());
                dto.setComplexity(question.getComplexity());
                List<Answer> answers = new ArrayList<>();
                for(AnswerEditYamlDTO a : question.getAnswers())
                {
                        Answer answer = new Answer();
                        answer.setQuestion(dto);
                        answers.add(AnswerEditYamlDTO.formatAnswer(a, answer));
                }
                return  answers;
        }
}
