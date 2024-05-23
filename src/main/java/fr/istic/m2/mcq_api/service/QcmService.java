package fr.istic.m2.mcq_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.istic.m2.mcq_api.domain.*;
import fr.istic.m2.mcq_api.dto.*;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QcmService {

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
    public QcmListDTO read(Long id) throws ResourceNotFoundException {
        Qcm qcm =   this.qcmRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Qcm", "id", id));
        return  convertToListDto(qcm);
    }

    public QcmListDTO create(QcmDTO qcmDTO) {
        Qcm qcm = this.formatQcm(qcmDTO, null);
        this.qcmRepository.saveAndFlush(qcm);
        return convertToListDto(qcm);
    }

    public QcmListDTO update(Long id, QcmDTO qcmDTO) throws ResourceNotFoundException {
        Qcm qcm = this.qcmRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Qcm", "id", id));
        qcm = this.formatQcm(qcmDTO, qcm);
        this.qcmRepository.saveAndFlush(qcm);
        return convertToListDto(qcm) ;
    }

    public void delete(Long id) {
        this.qcmRepository.deleteById(id);
    }

    public Qcm formatQcm(QcmDTO qcmDTO, Qcm qcm) throws ResourceNotFoundException {
        Level level = this.levelRepository.findById(qcmDTO.getLevelId()).orElseThrow(()-> new ResourceNotFoundException("Level", "id", qcmDTO.getLevelId()));
        Teacher teacher = this.teacherRepository.findById(qcmDTO.getTeacherId()).orElseThrow(()-> new ResourceNotFoundException("Teacher", "id", qcmDTO.getTeacherId()));
        if (qcm == null){
            qcm = new Qcm();
            qcm.setCreationDate(LocalDateTime.now());
        }
        qcm.setLevel(level);
        qcm.setTeacher(teacher);
        qcm.setDelay(qcmDTO.getDelay());
        qcm.setComplexity(qcm.getComplexity());
        qcm.setRandomActive(qcm.isRandomActive());
        qcm.setTitle(qcmDTO.getTitle());
        qcm.setOpenStartDate(qcmDTO.getOpenStartDate());
        qcm.setCloseStartDate(qcmDTO.getCloseStartDate());
        qcm.setUpdatedDate(LocalDateTime.now());
        qcm.setActive(qcmDTO.isActive());
        return qcm;
    }

    public static QcmListDTO convertToListDto(Qcm source){
        QcmListDTO result = new QcmListDTO();
        result.setId(source.getId());
        result.setActive(source.isActive());
        result.setDelay(source.getDelay());
        result.setLimitQuestion(source.getLimitQuestion());
        result.setOpenStartDate(source.getOpenStartDate());
        result.setCloseStartDate(source.getCloseStartDate());
        result.setQuestionCount(source.getQuestions().size());
        result.setTestCount(source.getStudentTestList().size());
        result.setCreationDate(source.getCreationDate());
        result.setUpdatedDate(source.getUpdatedDate());
        result.setTeacher(TeacherService.convertToTeacherList(source.getTeacher()));

        result.setRandomActive(source.isRandomActive());
        result.setTitle(source.getTitle());
        result.setComplexity(source.getComplexity());
        return result;
    }



    public List<Question> parseQCM(String content, QcmDTO dto) throws ResourceNotFoundException {
        //TODO Will be removed (make it dynamic : teacherId, levelId)
        Teacher teacher = teacherRepository.findById(dto.getTeacherId()).orElseThrow(()-> new ResourceNotFoundException("Teacher", "id", dto.getTeacherId()));
        Level level = levelRepository.findById(dto.getTeacherId()).orElseThrow(()-> new ResourceNotFoundException("Level", "id", dto.getLevelId()));
        Qcm qcm = formatQcm(dto, null);
        List<Question> questions = new ArrayList<>();
        String[] lines = content.split("\\r?\\n");

        Pattern questionPattern = Pattern.compile("^###\\s*(.+)$");
        Pattern levelPattern = Pattern.compile("^@@\\s*(\\d+)$");
        Pattern propositionPattern = Pattern.compile("^(\\+\\+|--)?\\s*(.+)$");

        Question currentQuestion = null;
        boolean hasLevel = false;
        StringBuilder currentText = new StringBuilder();
        String currentMarker = "";

        for (String line : lines) {
            Matcher questionMatcher = questionPattern.matcher(line);
            Matcher levelMatcher = levelPattern.matcher(line);
            Matcher propositionMatcher = propositionPattern.matcher(line);

            if (questionMatcher.matches()) {
                if (currentQuestion != null && currentMarker != null) {
                    finalizeCurrentEntry(currentQuestion, currentMarker, currentText.toString().trim(), hasLevel);
                }
                currentText.setLength(0);  // Clear the buffer
                currentText.append(questionMatcher.group(1).trim());
                currentMarker = "question";
                currentQuestion = new Question();
                currentQuestion.setQcm(qcm);
                questions.add(currentQuestion);
                hasLevel = false;
            } else if (levelMatcher.matches() && currentQuestion != null) {
                if (currentMarker.equals("question")) {
                    currentQuestion.setTitle(currentText.toString());
                } else if (!currentText.toString().trim().isEmpty()) {
                    throw new ResourceNotFoundException("Invalid format: unexpected level definition");
                }
                currentQuestion.setComplexity(Integer.parseInt(levelMatcher.group(1).trim()));
                hasLevel = true;
                currentText.setLength(0);  // Clear the buffer
                currentMarker = "level";
            } else if (propositionMatcher.matches() && currentQuestion != null) {
                if (currentMarker.equals("question")) {
                    currentQuestion.setTitle(currentText.toString());
                } else if (currentMarker.equals("proposition") && !currentText.toString().trim().isEmpty()) {
                    addPropositionToCurrentQuestion(currentQuestion, currentText.toString().trim(), currentMarker.equals("++"));
                }
                currentText.setLength(0);  // Clear the buffer
                currentText.append(propositionMatcher.group(2).trim());
                currentMarker = propositionMatcher.group(1);
            } else if (!line.trim().isEmpty()) {
                if (currentText.length() > 0) {
                    currentText.append("\n");
                }
                currentText.append(line.trim());
            }
        }

        if (currentQuestion != null && currentMarker!=null) {
            finalizeCurrentEntry(currentQuestion, currentMarker, currentText.toString().trim(), hasLevel);
        }

        if(questions.size() > 0){
            qcmRepository.saveAndFlush(qcm);
            questionRepository.saveAllAndFlush(questions);
            for(Question q: questions)
                answerRepository.saveAllAndFlush(q.getAnswers());
        }
        return questions;
    }

    private void finalizeCurrentEntry(Question question, String marker, String text, boolean hasLevel) throws ResourceNotFoundException {
        if (marker.equals("question")) {
            question.setTitle(text);
        } else if (marker.equals("level")) {
            if (!hasLevel) {
                throw new ResourceNotFoundException("Invalid format: missing level for question: " + question.getTitle());
            }
        } else if (marker.equals("++") || marker.equals("--")) {
            addPropositionToCurrentQuestion(question, text, marker.equals("++"));
        } else {
            throw new ResourceNotFoundException("Invalid format: unrecognized entry: " + text);
        }
    }

    private  void addPropositionToCurrentQuestion(Question question, String title, boolean isCorrect) {
        Answer answer = new Answer();
        answer.setTitle(title);
        answer.setValid(isCorrect);
        answer.setQuestion(question);
        //question.addProposition(new Proposition(title, isCorrect));
    }

    private  void saveAsJson(List<Question> questions, String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(new File(fileName), questions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public QcmJSONDTO exportQCMInJSON(Long id, HttpServletResponse response) throws ResourceNotFoundException, IOException {
        Qcm qcm = qcmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Qcm", "id", id));
        QcmJSONDTO listDTO = QcmJSONDTO.format(qcm);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String fileName = "export_qcm_" + qcm.getId() + "_" + LocalDateTime.now() + ".json";

        // Set response headers
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        // Write JSON to response
        mapper.writeValue(response.getWriter(), listDTO);

        return listDTO;
    }

    public QcmListDTO createQCMFromJSON(String json, QcmCreateJSONDto dto) throws IOException {
        QcmJSONDTO qcmJsonDto = parseQCMFromJSON(json);
        if(qcmJsonDto==null)
            throw new ResourceNotFoundException("Invalid format: missing qcm: ");

        Teacher teacher = teacherRepository.findById(dto.getTeacherId()).orElseThrow(()-> new ResourceNotFoundException("Teacher", "id", dto.getTeacherId()));
        Level level = levelRepository.findById(dto.getLevelId()).orElseThrow(()-> new ResourceNotFoundException("Level", "id", dto.getLevelId()));

        Qcm qcm = new Qcm();
        qcm.setTitle(qcmJsonDto.getTitle());
        qcm.setActive(qcmJsonDto.isActive());
        qcm.setComplexity(qcmJsonDto.getComplexity());
        qcm.setDelay(qcmJsonDto.getDelay());
        qcm.setRandomActive(qcmJsonDto.isRandomActive());
        qcm.setLimitQuestion(qcmJsonDto.getLimitQuestion());
        qcm.setOpenStartDate(qcmJsonDto.getOpenStartDate());
        qcm.setCloseStartDate(qcmJsonDto.getCloseStartDate());
        qcm.setLevel(level);
        qcm.setTeacher(teacher);

        //create Questions
        List<Answer> answerList = new ArrayList<>();
        List<Question> questionList = new ArrayList<>();

        for (QuestionJSONDTO q : qcmJsonDto.getQuestions())
        {
            Question question = new Question();
            question.setQcm(qcm);
            questionList.add(question);
            answerList.addAll(QuestionJSONDTO.formatToQuestion(q, question));

        }

        if(!questionList.isEmpty()){
            System.out.println("==============Question count "+questionList.size());
            System.out.println("==============Answer count "+answerList.size());
            qcmRepository.saveAndFlush(qcm);
            questionRepository.saveAllAndFlush(questionList);
            answerRepository.saveAllAndFlush(answerList);
        }
        return convertToListDto(qcm);
    }

    public QcmJSONDTO parseQCMFromJSON(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(json, QcmJSONDTO.class);
    }
}
