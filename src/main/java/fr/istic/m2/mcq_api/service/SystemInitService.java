package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.*;
import fr.istic.m2.mcq_api.dto.*;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Service to initialize the system and have default data for tesing purpose
 */
@Repository
public class SystemInitService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private QcmService qcmService;
    @Autowired
    private QcmRepository qcmRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private StudentTestRepository studentTestRepository;
    @Autowired
    private StudentTestAnswerRepository studentTestAnswerRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private YamlParserService yamlParserService;
    @Autowired
    private LevelService levelService;

    @Autowired
    private AnswerService answerService;


    static final int TEACHER_SIZE = 20;
    static final int MAX_COMPLEXITY = 10;
    static final int QCM_SIZE = 30;
    static final int QUESTION_PER_QCM = 5;
    static final int ANSWER_PER_QUESTION = 5;
    static final int STUDENT_SIZE = 10;
    static final int MAX_DURATION = 1000 * 3600; // One hour
    static final int TEST_PER_STUDENT = 10; // One hour
    static final int COMMENT_PER_QUESTION = 10; // One hour
    static final int COMMENT_PER_ANSWER = 10; // One hour

    public void init() throws  Exception{
        //Create two levels
        Level level1 = new Level();
        level1.setFieldOfStudy("MIAGE - DABI");
        level1.setClassOfStudy("M2");

        Level level2 = new Level();
        level2.setFieldOfStudy("MIAGE - DLIS");
        level2.setClassOfStudy("M2");
        levelRepository.saveAndFlush(level1);
        levelRepository.saveAndFlush(level2);

        //Create two students
        Student student1 = new Student();
        student1.setFirstName("Specteur");
        student1.setLastName("Harvey");
        student1.setUuid("000000001");
        student1.setStudentLevel(level1);

        Student student2 = new Student();
        student2.setFirstName("Cyr");
        student2.setLastName("Doms");
        student2.setUuid("000000002");
        student2.setStudentLevel(level2);
        studentRepository.saveAndFlush(student1);
        studentRepository.saveAndFlush(student2);

        //Create two teachers
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("James");
        teacher1.setLastName("Gosling");
        teacher1.setUuid("000000003");

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Anberree");
        teacher2.setLastName("Thomas");
        teacher2.setUuid("000000004");
        teacherRepository.saveAndFlush(teacher1);
        teacherRepository.saveAndFlush(teacher2);

        //Create QCM from Yamlfile
        String content = yamlParserService.getDefautQcmYamlString();
        QcmDTO qcmDto = new QcmDTO();
        qcmDto.setTitle("Culture Générale");
        qcmDto.setActive(true);
        qcmDto.setComplexity(3);
        qcmDto.setDelay(60);
        qcmDto.setRandomActive(true);
        qcmDto.setOpenStartDate(LocalDateTime.now());
        qcmDto.setTeacherId(teacher1.getId());
        qcmDto.setLevelId(level1.getId());
        Long qcmId = 0L;
        try {
           QcmListDTO qcmListDTO =  qcmService.createQCMFromYaml(content, qcmDto);
           qcmId = qcmListDTO.getId();
            } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Qcm qcm =   this.qcmRepository.findById(qcmId).orElseThrow(()-> new ResourceNotFoundException("Qcm", "id",0 ));
        List<Question> questions = questionRepository.findQuestionByQcmId(qcm.getId());

        if(!questions.isEmpty()){
            //Create StudentTest
            StudentTest studentTest = new StudentTest();
            studentTest.setStartingDate(LocalDateTime.now());
            studentTest.setStudent(student1);
            studentTest.setQcm(qcm);
            studentTestRepository.saveAndFlush(studentTest);

            for(Question q : questions){
                List<Answer> answers = answerRepository.findByQuestionId(q.getId());
                if(answers.isEmpty())
                    throw new Exception("Saved without studentTestAnswer because of answer");

                //Create StudentTestAnswer
                StudentTestAnswer studentTestAnswer = new StudentTestAnswer();
                studentTestAnswer.setDuration(new Random().nextInt(20));
                studentTestAnswer.setAnswer(answers.get(0));
                studentTestAnswer.setStudentTest(studentTest);
                studentTestAnswerRepository.saveAndFlush(studentTestAnswer);
                System.out.println("Système initialisé avec succès !");
            }
            this.initLevels();
            this.initStudents();
            this.initQcm();
            this.initStudentTestAnswer();
        } else throw new Exception("Saved without StudentTest because of question");

        // QCM 2
        //Create QCM from Yamlfile
        content = yamlParserService.getDefautQcmYamlString2();
        qcmDto = new QcmDTO();
        qcmDto.setTitle("Culture Générale 2");
        qcmDto.setActive(false);
        qcmDto.setComplexity(3);
        qcmDto.setDelay(60);
        qcmDto.setRandomActive(true);
        qcmDto.setOpenStartDate(LocalDateTime.now());
        qcmDto.setTeacherId(teacher1.getId());
        qcmDto.setLevelId(level1.getId());
        try {
            QcmListDTO qcmListDTO =  qcmService.createQCMFromYaml(content, qcmDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<Level> initLevels() {

        Map<String, String> levels = this.getLevels();
        List<Level> allLevels = new ArrayList<>();
        levels.forEach((classOfStudy, fieldOfStudy) -> {
            Level level = this.levelRepository.findOneByClassOfStudyAndFieldOfStudy(classOfStudy, fieldOfStudy);
            if (level == null){
                level = new Level();
                level.setClassOfStudy(classOfStudy);
                level.setFieldOfStudy(fieldOfStudy);
                level.setCreationDate(LocalDateTime.now());
                level.setUpdatedDate(LocalDateTime.now());
                this.levelRepository.save(level);
            }
            allLevels.add(level);
        });
        return allLevels;
    }



    public Map<String, String> getLevels(){
        Map<String, String> levels = new HashMap<>();
        levels.put("L1", "MIAGE");
        levels.put("L2", "MIAGE");
        levels.put("L3", "MIAGE");
        levels.put("M1", "MIAGE");
        levels.put("M2", "MIAGE");
        return levels;
    }


    public List<Teacher> getTeachers(){
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 1; i <= TEACHER_SIZE; i++) {
            String uuid = "TEACHER-00"+i;
            Teacher teacher = this.teacherRepository.findOneByUuid(uuid);
            if (teacher == null){
                teacher = new Teacher();
                teacher.setUuid(uuid);
                teacher.setFirstName("M. Teach  ");
                teacher.setFirstName("Dr. Teach "+i);
                teacher.setCreationDate(LocalDateTime.now());
                teacher.setUpdatedDate(LocalDateTime.now());
                this.teacherRepository.saveAndFlush(teacher);
            }
            teachers.add(teacher);
        }
        return teachers;
    }

    public List<Student> initStudents() {
        List<Level> levels = this.levelRepository.findAll();
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= STUDENT_SIZE; i++) {
            String uuid = "STUDENT-00"+i;
            Student student = this.studentRepository.findByUuid(uuid);
            if (student == null){
                student = new Student();
                student.setUuid(uuid);
                student.setFirstName("first_name "+i);
                student.setLastName("last_name "+i);
                student.setStudentLevel(levels.get((new Random()).nextInt(levels.size())));
                student.setCreationDate(LocalDateTime.now());
                student.setUpdatedDate(LocalDateTime.now());
                this.studentRepository.saveAndFlush(student);
            }

            students.add(student);
        }
        return students;
    }

    public List<Qcm> initQcm(){
        List<Level> levels = this.initLevels();
        List<Teacher> teachers = this.getTeachers();
        List<Qcm> qcmList = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();
        Random random = new Random();
        for (int i = 1; i <= QCM_SIZE; i++) {
            String title = "Qcm "+i;
            Qcm qcm = this.qcmRepository.findOneByTitle(title);
            Teacher teacher = teachers.get(random.nextInt(teachers.size()));
            Level level = levels.get(random.nextInt(levels.size()));
            if (qcm == null){
                qcm = new Qcm();
                qcm.setTitle(title);
                qcm.setDelay(random.nextInt(100));
                qcm.setLevel(level);
                qcm.setDetails("Detail QCM "+i);
                qcm.setLimitQuestion(QUESTION_PER_QCM);
                qcm.setActive(true);
                qcm.setTeacher(teacher);
                qcm.setComplexity(random.nextInt(MAX_COMPLEXITY));
                qcm.setCanShowResultToStudents(random.nextBoolean());
                qcm.setRandomActive(random.nextBoolean());
                qcm.setCloseStartDate(currentDate);
                qcm.setOpenStartDate(currentDate);
                qcm.setCreationDate(currentDate);
                qcm.setUpdatedDate(currentDate);
                for (int j = 1; j <= QUESTION_PER_QCM; j++) {
                    qcm.getQuestions().add(this.getRandomQuestion(qcm,  i, ANSWER_PER_QUESTION));
                }
                this.qcmRepository.saveAndFlush(qcm);
            }
            qcmList.add(qcm);
        }
        return qcmList;
    }


    public Question getRandomQuestion(Qcm qcm, int number, int sizeAnswers){
        LocalDateTime currentDate = LocalDateTime.now();
        Question question = new Question();
        Random random = new Random();
        question.setTitle("Question "+number+"[QCM="+qcm.getTitle()+"]");
        question.setQcm(qcm);
        question.setDelay(random.nextInt());
        question.setActive(random.nextBoolean());
        question.setComplexity(random.nextInt(MAX_COMPLEXITY));
        question.setCreationDate(currentDate);
        question.setUpdatedDate(currentDate);
        question.getComments().addAll(this.getQuestionComments(question));
        for (int i = 1; i <= sizeAnswers; i++) {
            question.getAnswers().add(this.getRandomAnswer(question, i));
        }
        return question;
    }

    public Answer getRandomAnswer(Question question, int id){
        Answer answer = new Answer();
        Random random = new Random();
        answer.setQuestion(question);
        answer.setValid(random.nextBoolean());
        answer.setTitle("Answer "+id);
        answer.setNbrPoint(random.nextInt(10));
        answer.setActive(random.nextBoolean());
        answer.setCreationDate(LocalDateTime.now());
        answer.setUpdatedDate(LocalDateTime.now());
        answer.getComments().addAll(this.getAnswerComments(answer));
        return answer;
    }


    public void initStudentTestAnswer(){
        List<Student> students = this.initStudents();
        List<Qcm> qcmList  = this.initQcm();
        Random random = new Random();
        for (Student student : students) {
            for (int k = 0; k < TEST_PER_STUDENT; k++) {
                AnswerQcmDTO answerQcmDTO = new AnswerQcmDTO();
                answerQcmDTO.setStudentId(student.getId());

                Qcm qcm = qcmList.get(random.nextInt(qcmList.size()));

                List<Question> questions = qcm.getQuestions();
                // Choose the number of question to answer
                int totalAnswer = random.nextInt(questions.size());
                // Store question already replied index
                List<Integer> questionsChosen = new ArrayList<>();

                // Reply randomly to qcm's question
                for (int i = 0; i < totalAnswer; i++) {
                    int randInt = random.nextInt(questions.size());;
                    // Choose random index of question to reply
                    while (questionsChosen.contains(randInt)){
                        randInt = random.nextInt(questions.size());
                    }
                    questionsChosen.add(randInt);
                    Question question = questions.get(randInt);
                    List<Answer> answers = question.getAnswers();
                    // Choose random answer for question
                    Answer answer = answers.get(random.nextInt(answers.size()));

                    QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
                    questionAnswerDTO.setAnswerId(answer.getId());
                    questionAnswerDTO.setQuestionId(question.getId());
                    questionAnswerDTO.setDuration(random.nextInt(MAX_DURATION));

                    answerQcmDTO.getAnswers().add(questionAnswerDTO);
                }
                // Save student answer
                answerService.answersQcm(qcm.getId(), answerQcmDTO);
            }
        }
    }


    public List<QuestionComment> getQuestionComments(Question question){
        List<QuestionComment> questionCommentList = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();
        for (int i = 1; i <= COMMENT_PER_QUESTION; i++) {
            QuestionComment questionComment = new QuestionComment();
            questionComment.setQuestion(question);
            questionComment.setAccepted(false);
            questionComment.setSuggestion("Ceci est une suggestion "+1);
            questionComment.setDescription("Ceci est une description "+1);
            questionComment.setCreationDate(currentDate);
            questionComment.setUpdatedDate(currentDate);
            questionCommentList.add(questionComment);
        }
        return questionCommentList;
    }

    public List<AnswerComment> getAnswerComments(Answer answer){
        List<AnswerComment> answerCommentList = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();
        for (int i = 1; i <= COMMENT_PER_QUESTION; i++) {
            AnswerComment answerComment = new AnswerComment();
            answerComment.setAnswer(answer);
            answerComment.setAccepted(false);
            answerComment.setSuggestion("Ceci est une suggestion "+1);
            answerComment.setDescription("Ceci est une description "+1);
            answerComment.setCreationDate(currentDate);
            answerComment.setUpdatedDate(currentDate);
            answerCommentList.add(answerComment);
        }
        return answerCommentList;
    }

}
