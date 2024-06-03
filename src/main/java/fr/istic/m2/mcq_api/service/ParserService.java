package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.*;
import fr.istic.m2.mcq_api.dto.QcmWithTextDTO;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class ParserService {

    @Autowired
    private QcmRepository qcmRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private QuestionService questionService;
    List<Question> questions = new ArrayList<>();
    Question currentQuestion = null;
    private final String DELAY_KEY = "delay";
    private final String COMPLEXITY_KEY = "complexity";
    private final String IS_ACTIVE = "active";
    private final String VALID_ANSWER = "+";
    private final String UN_VALID_ANSWER = "-";
    public List<Question> parseQCM(String content, Qcm qcm) throws Exception {
        String[] lines = content.split("\\r?\\n");
        //commence par un astérisque suivi d'un espace et capture le texte qui suit
        Pattern questionPattern = Pattern.compile("^\\*\\s*(.+)$");
        //commence par :delay, :begindate, ou :complexity, suivi d'un espace et capture le texte qui suit
        Pattern metaPattern = Pattern.compile("^:(delay|active|complexity)\\s+(.+)$");
        // commence par + ou -, suivi d'un espace et capture le texte qui suit. Il gère également le texte sur plusieurs lignes.
        Pattern propositionPattern = Pattern.compile("^(\\+|-)\\s*(.+)$");



        boolean hasLevel = false;
        StringBuilder currentText = new StringBuilder();
        String currentMarker = "";

        for (String line : lines) {
            Matcher questionMatcher = questionPattern.matcher(line);
            Matcher metaMatcher = metaPattern.matcher(line);
            Matcher propositionMatcher = propositionPattern.matcher(line);

            if (questionMatcher.matches()) {
                //We found new question while we already have one
                if (currentQuestion != null && currentMarker != null) {
                    saveAndCloseQuestion(currentQuestion);
                }
                currentText.setLength(0);  // Clear the buffer
                currentText.append(questionMatcher.group(1).trim());
                currentMarker = "question";
                currentQuestion = new Question();
                currentQuestion.setQcm(qcm);
                questions.add(currentQuestion);
            } else if (metaMatcher.matches()) {
                if(currentQuestion==null)
                    throw new ResourceNotFoundException("Invalid format: You should have question before question detail ["+propositionMatcher.group(1).trim()+","+propositionMatcher.group(2).trim()+"]");

                if (currentMarker.equals("question")) {
                    currentQuestion.setTitle(currentText.toString());
                } else if (!currentText.toString().trim().isEmpty()) {
                    throw new ResourceNotFoundException("Invalid format: unexpected meta definition");
                }
                saveMeta(currentQuestion, metaMatcher.group(1).trim(), metaMatcher.group(2).trim());
                //hasLevel = true;
                currentText.setLength(0);  // Clear the buffer
                currentMarker = "meta";
            } else if (propositionMatcher.matches()) {
                if(currentQuestion==null)
                    throw new ResourceNotFoundException("Invalid format: You should have question before answer ["+propositionMatcher.group(2).trim()+"]");

                if (currentMarker.equals("question")) {
                    currentQuestion.setTitle(currentText.toString());
                } else if ((currentMarker.equals(VALID_ANSWER) || currentMarker.equals(UN_VALID_ANSWER))  && !currentText.toString().trim().isEmpty()) {
                    addPropositionToCurrentQuestion(currentQuestion, currentText.toString().trim(), currentMarker.equals(VALID_ANSWER));
                }
                currentText.setLength(0);  // Clear the buffer
                currentText.append(propositionMatcher.group(2).trim());
                currentMarker = propositionMatcher.group(1).trim();
            } else if (!line.trim().isEmpty()) {
                //We skip the white line
                if (currentText.length() > 0) {
                    currentText.append("\n");
                }
                currentText.append(line.trim());
            }
        }

        if (currentQuestion != null && currentMarker!=null) {
            saveAndCloseQuestion(currentQuestion);
        }

        if(questions.size() > 0){
            qcmRepository.saveAndFlush(qcm);
            questionRepository.saveAllAndFlush(questions);
            for(Question q: questions)
                answerRepository.saveAllAndFlush(q.getAnswers());
        }
        return questions;
    }

    private void saveAndCloseQuestion(Question question) throws ResourceNotFoundException {
       if(question != null && !question.getAnswers().isEmpty()){
           questions.add(question);
           currentQuestion = null;
       }else  throw new ResourceNotFoundException("Invalid format: missing answers for question: " + question.getTitle());

    }


    private  void addPropositionToCurrentQuestion(Question question, String title, boolean isCorrect) {
        Answer answer = new Answer();
        answer.setTitle(title);
        answer.setValid(isCorrect);
        answer.setQuestion(question);
        question.getAnswers().add(answer);
    }

    private void saveMeta(Question question, String key, String value) throws  Exception{
        System.out.println("key="+key+ ", value="+value);
        switch (key.replaceAll("\"", "")){
            case DELAY_KEY:
                question.setDelay(Integer.parseInt(value));
                break;
            case COMPLEXITY_KEY:
                question.setComplexity(Integer.parseInt(value));
                break;
            case IS_ACTIVE:
                question.setActive(Boolean.parseBoolean(value));
                break;
        }
    }
}
