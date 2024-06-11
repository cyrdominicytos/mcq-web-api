package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.*;
import fr.istic.m2.mcq_api.dto.*;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

}
