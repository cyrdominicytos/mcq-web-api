package fr.istic.m2.mcq_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.istic.m2.mcq_api.domain.*;
import fr.istic.m2.mcq_api.dto.*;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    @Autowired
    private QuestionService questionService;
    @PersistenceContext
    private EntityManager em;
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

    public List<Question> parseQCM(QcmWithTextDTO dto) throws ResourceNotFoundException {
        String content = dto.getText();
        Teacher teacher = teacherRepository.findById(dto.getDto().getTeacherId()).orElseThrow(()-> new ResourceNotFoundException("Teacher", "id", dto.getDto().getTeacherId()));
        Level level = levelRepository.findById(dto.getDto().getLevelId()).orElseThrow(()-> new ResourceNotFoundException("Level", "id", dto.getDto().getLevelId()));
        Qcm qcm = formatQcm(dto.getDto(), null);
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

    /**
     * Create a QCM from a given  Yaml content
     * @param content, the yaml content
     * @param dto, the QCM attributes
     * @return
     * @throws IOException , if the yamel file is invalid
     */
    public QcmListDTO createQCMFromYaml(String content, QcmDTO dto) throws Exception {

        Qcm qcm = formatQcm(dto, null);
        List<QuestionYamlDTO> questions = YamlParserService.parseYaml(content);
        if(questions.isEmpty())
            throw new ResourceNotFoundException("Invalid format: missing questions: ");
        //create Questions
        List<Answer> answerList = new ArrayList<>();
        List<Question> questionList = new ArrayList<>();

        for (QuestionYamlDTO q : questions)
        {
            Question question = new Question();
            question.setQcm(qcm);
            questionList.add(question);
            answerList.addAll(QuestionYamlDTO.formatQuestion(q, question));

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

    @Transactional
    public QcmListDTO updateQcmFromYaml1(Long qcmId, String yamlContent, QcmDTO dto) throws Exception {
        Qcm qcm = qcmRepository.findById(qcmId)
                .orElseThrow(() -> new ResourceNotFoundException("QCM not found with id " + qcmId));
        qcm = formatQcm(dto, qcm);

        YamlParsersService<QcmEditYamlDTO> parser = new YamlParsersService<>();
        List<QuestionEditYamlDTO> questionYamlDTOs = parser.parseYaml(yamlContent, QcmEditYamlDTO.class).getQuestions();

        // Map for existing questions by ID
        Map<Long, Question> existingQuestionsById = qcm.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, question -> question));

        Set<Long> updatedQuestionIds = new HashSet<>();
        Set<Long> updatedAnswerIds = new HashSet<>();

        for (QuestionEditYamlDTO questionYamlDTO : questionYamlDTOs) {
            Question question;
            if (questionYamlDTO.getQuestionId() != null && questionYamlDTO.getQuestionId() > 0) {
                question = existingQuestionsById.get(questionYamlDTO.getQuestionId());
                if (question == null || !question.getQcm().getId().equals(qcmId)) {
                    throw new IllegalArgumentException("Invalid question ID: " + questionYamlDTO.getQuestionId());
                }
            } else {
                question = new Question();
                question.setQcm(qcm);
            }

            question.setTitle(questionYamlDTO.getTitle());
            question.setActive(questionYamlDTO.isActive());
            question.setDelay(questionYamlDTO.getDelay());
            question.setComplexity(questionYamlDTO.getComplexity());

            // Map for existing answers by ID
            Map<Long, Answer> existingAnswersById = question.getAnswers().stream()
                    .collect(Collectors.toMap(Answer::getId, answer -> answer));

            List<Answer> updatedAnswers = new ArrayList<>();
            for (AnswerEditYamlDTO answerYamlDTO : questionYamlDTO.getAnswers()) {
                Answer answer;
                if (answerYamlDTO.getAnswerId() != null && answerYamlDTO.getAnswerId() > 0) {
                    answer = existingAnswersById.get(answerYamlDTO.getAnswerId());
                    if (answer == null || !answer.getQuestion().getId().equals(question.getId())) {
                        throw new IllegalArgumentException("Invalid answer ID: " + answerYamlDTO.getAnswerId());
                    }
                } else {
                    answer = new Answer();
                    answer.setQuestion(question);
                }

                answer.setTitle(answerYamlDTO.getTitle());
                answer.setActive(answerYamlDTO.isActive());
                answer.setValid(answerYamlDTO.isValid());
                updatedAnswers.add(answer);
                updatedAnswerIds.add(answer.getId());
            }

            question.setAnswers(updatedAnswers);
            updatedQuestionIds.add(question.getId());

            if (question.getId() == null) {
                questionRepository.save(question); // new question
            } else {
                questionRepository.saveAndFlush(question); // update existing question
            }
        }

        // Remove deleted questions
        List<Question> questionsToRemove = new ArrayList<>();
        for (Question existingQuestion : qcm.getQuestions()) {
            if (!updatedQuestionIds.contains(existingQuestion.getId())) {
                questionsToRemove.add(existingQuestion);
            } else {
                // Remove deleted answers
                List<Answer> answersToRemove = new ArrayList<>();
                for (Answer existingAnswer : existingQuestion.getAnswers()) {
                    if (!updatedAnswerIds.contains(existingAnswer.getId())) {
                        answersToRemove.add(existingAnswer);
                    }
                }
                for (Answer answerToRemove : answersToRemove) {
                    existingQuestion.getAnswers().remove(answerToRemove);
                    answerRepository.delete(answerToRemove);
                }
            }
        }

        for (Question questionToRemove : questionsToRemove) {
            qcm.getQuestions().remove(questionToRemove);
            questionRepository.delete(questionToRemove);
        }

        qcmRepository.saveAndFlush(qcm);

        return convertToListDto(qcm);
    }

    @Transactional
    public QcmListDTO updateQcmFromYaml(Long qcmId, String yamlContent,QcmDTO dto ) throws Exception {
        Qcm qcm = qcmRepository.findById(qcmId)
                .orElseThrow(() -> new ResourceNotFoundException("QCM not found with id " + qcmId));
        qcm = formatQcm(dto, qcm);

        YamlParsersService<QcmEditYamlDTO> parser = new YamlParsersService<>();
        List<QuestionEditYamlDTO> questionYamlDTOs = parser.parseYaml(yamlContent, QcmEditYamlDTO.class).getQuestions();

        // Map for existing questions by ID
        //System.out.println("==============qcm.getQuestions() count "+qcm.getQuestions().size());
        Map<Long, Question> existingQuestionsById = qcm.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, question -> question));

        List<Question> questionToSaveOrUpdate = new ArrayList<>();
        List<Long> answerIdsToDelete = new ArrayList<>();
        List<Question> questionsToRemove = new ArrayList<>();
        List<Answer> answerToSaveOrUpdate = new ArrayList<>();

        for (QuestionEditYamlDTO questionYamlDTO : questionYamlDTOs) {
            //Repérer les anciènnes questions
            Question question;
            if (questionYamlDTO.getQuestionId() != null && questionYamlDTO.getQuestionId() > 0) {
                question = existingQuestionsById.remove(questionYamlDTO.getQuestionId());

                //question = existingQuestionsById.get(questionYamlDTO.getQuestionId());
                if (question == null || !question.getQcm().getId().equals(qcmId)) {
                    throw new IllegalArgumentException("Invalid question ID: " + questionYamlDTO.getQuestionId());
                }
            } else {
                question = new Question();
                question.setQcm(qcm);
            }

            question.setTitle(questionYamlDTO.getTitle());
            question.setActive(questionYamlDTO.isActive());
            question.setDelay(questionYamlDTO.getDelay());
            question.setComplexity(questionYamlDTO.getComplexity());
            questionToSaveOrUpdate.add(question);

            // Map for existing answers by ID
            //System.out.println("==============question.getAnswers() count "+question.getAnswers().size());
            Map<Long, Answer> existingAnswersById = question.getAnswers().stream()
                    .collect(Collectors.toMap(Answer::getId, answer -> answer));

            List<Answer> updatedAnswers = new ArrayList<>();
            for (AnswerEditYamlDTO answerYamlDTO : questionYamlDTO.getAnswers()) {
                Answer answer;
                if (answerYamlDTO.getAnswerId() != null && answerYamlDTO.getAnswerId() > 0) {
                    answer = existingAnswersById.remove(answerYamlDTO.getAnswerId());
                    //answer = existingAnswersById.get(answerYamlDTO.getAnswerId());
                    if (answer == null || !answer.getQuestion().getId().equals(question.getId())) {
                        throw new IllegalArgumentException("Invalid answer ID: " + answerYamlDTO.getAnswerId());
                    }
                } else {
                    answer = new Answer();
                    answer.setQuestion(question);
                }

                answer.setTitle(answerYamlDTO.getTitle());
                answer.setActive(answerYamlDTO.isActive());
                answer.setValid(answerYamlDTO.isValid());
                updatedAnswers.add(answer);
                //updatedAnswerIds.add(answer.getId());
                answerToSaveOrUpdate.add(answer);
            }
            answerIdsToDelete.addAll(existingAnswersById.keySet());
            question.setAnswers(updatedAnswers);
            //updatedQuestionIds.add(question.getId());

        }

        //save new and update old Questions
        System.out.println("======== saving questionToSaveOrUpdate...========");
        questionRepository.saveAll(questionToSaveOrUpdate);
        //delete removed Answers
        System.out.println("======== deleting answers...========");
        answerRepository.deleteAllById(answerIdsToDelete);



        /*for(Long questionId : existingQuestionsById.keySet())
        {
           //Question q = qcm.removeQuestionById(questionId);
            System.out.println("======== deleting answers...========");
           //answerRepository.deleteAll(q.getAnswers());
            System.out.println("======== deleting questions...========");
            questionRepository.deleteAllById(existingQuestionsById.keySet());
          //questionRepository.delete(q);
           //em.remove(q);

        }*/
        System.out.println("======== deleting questions...========");
        questionRepository.deleteAllById(existingQuestionsById.keySet());
        System.out.println("======== saving qcm...========");
        qcmRepository.saveAndFlush(qcm);
        // Delete questions from the repository
        //questionRepository.deleteAllById(existingQuestionsById.keySet());
        System.out.println("======== flushing all...========");
        //em.flush();
        /*Iterator<Question> iterator = qcm.getQuestions().iterator();
        List<Question> questionsToDelete = new ArrayList<>();
        while (iterator.hasNext()) {
            Question q = iterator.next();
            if (existingQuestionsById.keySet().contains(q.getId())) {
                System.out.println("======== Contain=true========");
                questionsToDelete.add(q);
                iterator.remove(); // Use iterator to remove element

                em.remove(q);
                q.setQcm(null);
            }
        }
        em.flush();*/


       // answerRepository.saveAllAndFlush(answerToSaveOrUpdate);
       //questionRepository.deleteAllById( (List<Long>) existingQuestionsById.keySet());

        //delete removed questions
        /*for(Question q : qcm.getQuestions())
        {
            if(existingQuestionsById.keySet().contains(q.getId()))
            {
                System.out.println("======== Contain=true========");
                qcm.getQuestions().remove(q);
            }
        }*/

        // Delete removed questions
        // Collect questions to delete
        /*Iterator<Question> iterator = qcm.getQuestions().iterator();
        List<Question> questionsToDelete = new ArrayList<>();
        while (iterator.hasNext()) {
            Question q = iterator.next();
            if (existingQuestionsById.keySet().contains(q.getId())) {
                System.out.println("======== Contain=true========");
                questionsToDelete.add(q);
                iterator.remove(); // Use iterator to remove element

                em.remove(q);
               q.setQcm(null);
            }
        }
        em.flush();*/

        // Delete questions from the repository
       // questionRepository.deleteAllById(existingQuestionsById.keySet());
        //qcmRepository.save(qcm);
        //em.flush();
        //questionRepository.deleteAll(questionsToDelete);

        /*<Question> iterator = qcm.getQuestions().iterator();
        while (iterator.hasNext()) {
            Question q = iterator.next();
            if (existingQuestionsById.keySet().contains(q.getId())) {
                System.out.println("======== Contain=true========");
                iterator.remove(); // Use iterator to remove element
            }
        }*/

        System.out.println("======== converting ...========");
        //em.getTransaction().commit();
        return convertToListDto(qcm);
    }
//     for(Long id: existingQuestionsById.keySet())
//            qcm.removeQuestionById(id);

    @Transactional
    public QcmListDTO updateQcmFromYamlR(Long qcmId, String yamlContent, QcmDTO dto) throws Exception {
        Qcm qcm = qcmRepository.findById(qcmId)
                .orElseThrow(() -> new ResourceNotFoundException("QCM not found with id " + qcmId));
        qcm = formatQcm(dto, qcm);

        YamlParsersService<QcmEditYamlDTO> parser = new YamlParsersService<>();
        List<QuestionEditYamlDTO> questionYamlDTOs = parser.parseYaml(yamlContent, QcmEditYamlDTO.class).getQuestions();

        // Map for existing questions by ID
        System.out.println("==============qcm.getQuestions() count " + qcm.getQuestions().size());
        Map<Long, Question> existingQuestionsById = qcm.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, question -> question));

        List<Question> questionsToSaveOrUpdate = new ArrayList<>();
        List<Long> answerIdsToDelete = new ArrayList<>();
        List<Long> questionIdsToDelete = new ArrayList<>();
        List<Answer> answersToSaveOrUpdate = new ArrayList<>();

        for (QuestionEditYamlDTO questionYamlDTO : questionYamlDTOs) {
            // Repérer les anciennes questions
            Question question;
            if (questionYamlDTO.getQuestionId() != null && questionYamlDTO.getQuestionId() > 0) {
                question = existingQuestionsById.remove(questionYamlDTO.getQuestionId());
                if (question == null || !question.getQcm().getId().equals(qcmId)) {
                    throw new IllegalArgumentException("Invalid question ID: " + questionYamlDTO.getQuestionId());
                }
            } else {
                question = new Question();
                question.setQcm(qcm);
            }

            question.setTitle(questionYamlDTO.getTitle());
            question.setActive(questionYamlDTO.isActive());
            question.setDelay(questionYamlDTO.getDelay());
            question.setComplexity(questionYamlDTO.getComplexity());
            questionsToSaveOrUpdate.add(question);

            // Map for existing answers by ID
            System.out.println("==============question.getAnswers() count " + question.getAnswers().size());
            Map<Long, Answer> existingAnswersById = question.getAnswers().stream()
                    .collect(Collectors.toMap(Answer::getId, answer -> answer));

            List<Answer> updatedAnswers = new ArrayList<>();
            for (AnswerEditYamlDTO answerYamlDTO : questionYamlDTO.getAnswers()) {
                Answer answer;
                if (answerYamlDTO.getAnswerId() != null && answerYamlDTO.getAnswerId() > 0) {
                    answer = existingAnswersById.remove(answerYamlDTO.getAnswerId());
                    if (answer == null || !answer.getQuestion().getId().equals(question.getId())) {
                        throw new IllegalArgumentException("Invalid answer ID: " + answerYamlDTO.getAnswerId());
                    }
                } else {
                    answer = new Answer();
                    answer.setQuestion(question);
                }

                answer.setTitle(answerYamlDTO.getTitle());
                answer.setActive(answerYamlDTO.isActive());
                answer.setValid(answerYamlDTO.isValid());
                updatedAnswers.add(answer);
                answersToSaveOrUpdate.add(answer);
            }
            answerIdsToDelete.addAll(existingAnswersById.keySet());
            question.setAnswers(updatedAnswers);
        }

        // Save new and update old Questions and Answers
        System.out.println("==============1");
        questionRepository.saveAllAndFlush(questionsToSaveOrUpdate);
        System.out.println("==============2");
        answerRepository.saveAllAndFlush(answersToSaveOrUpdate);
        System.out.println("==============3");
        // Collect questions to delete
        for (Question q : qcm.getQuestions()) {
            if (existingQuestionsById.containsKey(q.getId())) {
                System.out.println("======== Contain=true ========");
                questionIdsToDelete.add(q.getId());
            }
        }

        // Delete removed Answers
        System.out.println("==============4");
        answerRepository.deleteAllById(answerIdsToDelete);

        // Remove references from QCM before deleting questions
        System.out.println("==============5");
        qcm.getQuestions().removeIf(q -> questionIdsToDelete.contains(q.getId()));
        qcmRepository.saveAndFlush(qcm);

        // Now delete questions from the repository
        System.out.println("==============6");
        questionRepository.deleteAllById(questionIdsToDelete);
        System.out.println("==============7");
        qcmRepository.saveAndFlush(qcm);
        System.out.println("==============8");
        return convertToListDto(qcm);
    }

    @Transactional
    public QcmListDTO updateQcmFromYaml_old(Long qcmId, String yamlContent,QcmDTO dto ) throws Exception {
        Qcm qcm = qcmRepository.findById(qcmId)
                .orElseThrow(() -> new ResourceNotFoundException("QCM not found with id " + qcmId));
        qcm = formatQcm(dto, qcm);

        YamlParsersService<QcmEditYamlDTO> parser = new YamlParsersService<>();
        List<QuestionEditYamlDTO> questionYamlDTOs = parser.parseYaml(yamlContent, QcmEditYamlDTO.class).getQuestions();

        // Map for existing questions by ID
        System.out.println("==============qcm.getQuestions() count "+qcm.getQuestions().size());
        Map<Long, Question> existingQuestionsById = qcm.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, question -> question));

        Set<Long> updatedQuestionIds = new HashSet<>();
        Set<Long> updatedAnswerIds = new HashSet<>();

        for (QuestionEditYamlDTO questionYamlDTO : questionYamlDTOs) {
            Question question;
            if (questionYamlDTO.getQuestionId() != null && questionYamlDTO.getQuestionId() > 0) {
                question = existingQuestionsById.get(questionYamlDTO.getQuestionId());
                if (question == null || !question.getQcm().getId().equals(qcmId)) {
                    throw new IllegalArgumentException("Invalid question ID: " + questionYamlDTO.getQuestionId());
                }
            } else {
                question = new Question();
                question.setQcm(qcm);
            }

            question.setTitle(questionYamlDTO.getTitle());
            question.setActive(questionYamlDTO.isActive());
            question.setDelay(questionYamlDTO.getDelay());
            question.setComplexity(questionYamlDTO.getComplexity());

            // Map for existing answers by ID
            System.out.println("==============question.getAnswers() count "+question.getAnswers().size());
            Map<Long, Answer> existingAnswersById = question.getAnswers().stream()
                    .collect(Collectors.toMap(Answer::getId, answer -> answer));

            List<Answer> updatedAnswers = new ArrayList<>();
            for (AnswerEditYamlDTO answerYamlDTO : questionYamlDTO.getAnswers()) {
                Answer answer;
                if (answerYamlDTO.getAnswerId() != null && answerYamlDTO.getAnswerId() > 0) {
                    answer = existingAnswersById.get(answerYamlDTO.getAnswerId());
                    if (answer == null || !answer.getQuestion().getId().equals(question.getId())) {
                        throw new IllegalArgumentException("Invalid answer ID: " + answerYamlDTO.getAnswerId());
                    }
                } else {
                    answer = new Answer();
                    answer.setQuestion(question);
                }

                answer.setTitle(answerYamlDTO.getTitle());
                answer.setActive(answerYamlDTO.isActive());
                answer.setValid(answerYamlDTO.isValid());
                updatedAnswers.add(answer);
                updatedAnswerIds.add(answer.getId());
            }

            question.setAnswers(updatedAnswers);
            updatedQuestionIds.add(question.getId());

            if (question.getId() == null) {
                questionRepository.save(question); // new question
            } else {
                questionRepository.saveAndFlush(question); // update existing question
            }
        }
        System.out.println("==============before saving count "+qcm.getQuestions().size());
        qcmRepository.saveAndFlush(qcm);
        System.out.println("==============after saving count "+qcm.getQuestions().size());
        // Remove deleted questions
      for (Question existingQuestion : qcm.getQuestions()) {
            if (!updatedQuestionIds.contains(existingQuestion.getId())) {
                System.out.println("==============before deleting questionRepository==========");
                questionRepository.deleteById(existingQuestion.getId());
                System.out.println("==============after deleting questionRepository==========");
            } else {
                // Remove deleted answers
                for (Answer existingAnswer : existingQuestion.getAnswers()) {
                    if (!updatedAnswerIds.contains(existingAnswer.getId())) {
                        System.out.println("==============before deleting answerRepository==========");
                        answerRepository.deleteById(existingAnswer.getId());
                        System.out.println("==============after deleting answerRepository==========");
                    }
                }
            }
        }
        System.out.println("==============before saving count "+qcm.getQuestions().size());
        qcmRepository.saveAndFlush(qcm);
        System.out.println("==============after saving count "+qcm.getQuestions().size());
        return convertToListDto(qcm);
    }

    public QcmJSONDTO parseQCMFromJSON(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(json, QcmJSONDTO.class);
    }
    public List<Qcm> getAll() {
        return this.qcmRepository.findAll();
    }

    public List<Qcm> getAllTeacherId(Long id) {
        return this.qcmRepository.findAllByTeacherId(id);

    }
}
