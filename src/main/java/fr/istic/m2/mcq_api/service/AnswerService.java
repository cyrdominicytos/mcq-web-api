package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.*;
import fr.istic.m2.mcq_api.dto.*;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    private final QcmRepository qcmRepository;
    private final StudentRepository studentRepository;
    private final StudentTestRepository studentTestRepository;


    @Autowired
    public AnswerService(
            QcmRepository qcmRepository,
            StudentRepository studentRepository,
            StudentTestRepository studentTestRepository
            ){
        this.qcmRepository = qcmRepository;
        this.studentRepository = studentRepository;
        this.studentTestRepository = studentTestRepository;
    }

    public Answer read(Long id) {
        return this.answerRepository.findById(id).orElse(null);
    }

    public Answer create(AnswerDTO answerDTO) {
        Question question = this.questionRepository
                                .findById(answerDTO.getQuestionId())
                                .get();
        Answer answer = this.formatAnswer(answerDTO, question);
        this.answerRepository.saveAndFlush(answer);
        return answer;
    }

    public Answer update(Long id, AnswerDTO answerDTO) throws ResourceNotFoundException {
        Answer answer = this.answerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Answer", "id", id));
        Question question = this.questionRepository.findById(answerDTO.getQuestionId()).orElseThrow(()-> new ResourceNotFoundException("Question", "id", answerDTO.getQuestionId()));
        answer.setValid(answerDTO.isValid());
        answer.setActive(answerDTO.isActive());
        answer.setNbrPoint(answerDTO.getNbrPoint());
        answer.setQuestion(question);
        answer.setUpdatedDate(LocalDateTime.now());
        this.answerRepository.saveAndFlush(answer);
        return answer;
    }

    public void delete(Long id) {
        this.answerRepository.deleteById(id);
    }

    public Answer formatAnswer(AnswerDTO answerDTO, Question question){
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setTitle(answerDTO.getTitle());
        answer.setActive(answerDTO.isActive());
        answer.setNbrPoint(answerDTO.getNbrPoint());
        answer.setCreationDate(LocalDateTime.now());
        answer.setValid(answer.isValid());
        return answer;
    }

    public static AnswerListDTO convertToListDto(Answer source){
        AnswerListDTO result = new AnswerListDTO();
        result.setId(source.getId());
        result.setActive(source.isActive());
        result.setTitle(source.getTitle());
        result.setValid(source.isValid());
        result.setNbrPoint(source.getNbrPoint());
        result.setQuestionId(source.getQuestion().getId());
        result.setCreationDate(source.getCreationDate());
        result.setUpdatedDate(source.getUpdatedDate());
        result.setCommentCount(source.getComments().size());
        result.setStudentTestAnswerCount(source.getStudentTestAnswers().size());
        return result;
    }

    public List<Answer> getAllByQuestion(Long id) {
        return this.answerRepository.findByQuestionId(id);
    }

    public List<Answer> getAll() {
        return this.answerRepository.findAll();
    }

    public void answersQcm(Long id, AnswerQcmDTO answers) throws ResourceNotFoundException {
        Qcm qcm = this.qcmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Qcmid= "+id+" not not found"));
        Student student = this.studentRepository.findById(answers.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student id not found"));
        List<QuestionAnswerDTO> questionAnswerDTOs = answers.getAnswers();
        for(QuestionAnswerDTO q : questionAnswerDTOs){
            boolean matched = this.isAnswerMatchWithQuestion(q);
            if (!matched){
                throw new ResourceNotFoundException(String.format("questionId=%d not matching with answerId=%d", q.getQuestionId(), q.getAnswerId()));
            }
        }
        StudentTest studentTest = new StudentTest();
        studentTest.setQcm(qcm);
        studentTest.setStudent(student);
        LocalDateTime now = LocalDateTime.now();
        studentTest.setCreationDate(now);
        studentTest.setUpdatedDate(now);
        studentTest.setStartingDate(now); // Need review TODO
        studentTest.setEndDate(now); // Need review TODO

        for(QuestionAnswerDTO q : questionAnswerDTOs){
            StudentTestAnswer studentTestAnswer = new StudentTestAnswer();
            Answer answer = this.answerRepository.findById(q.getAnswerId()).get();
            studentTestAnswer.setStudentTest(studentTest);
            studentTestAnswer.setAnswer(answer);
            studentTestAnswer.setCreationDate(now);
            studentTestAnswer.setUpdatedDate(now);
            studentTestAnswer.setDuration(q.getDuration());
            studentTest.getStudentTestAnswer().add(studentTestAnswer); // Add each student answer
        }

        this.studentTestRepository.saveAndFlush(studentTest);

    }


    /**
     * check if answer id is link with question id
     * @param questionAnswerDTO
     * @return
     */
    private boolean isAnswerMatchWithQuestion(QuestionAnswerDTO questionAnswerDTO){
        Answer answer = this.answerRepository.findById(questionAnswerDTO.getAnswerId()).orElse(null);
        if (answer == null){
            return false;
        }
        if (answer.getQuestion().getId() != questionAnswerDTO.getQuestionId()){
            return false;
        }
        return true;
    }
}
