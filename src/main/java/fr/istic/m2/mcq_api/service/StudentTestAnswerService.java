package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.*;
import fr.istic.m2.mcq_api.dto.StudentTestAnswerDto;
import fr.istic.m2.mcq_api.dto.StudentTestAnswerListDto;
import fr.istic.m2.mcq_api.dto.StudentTestDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.AnswerRepository;
import fr.istic.m2.mcq_api.repository.StudentTestAnswerRepository;
import fr.istic.m2.mcq_api.repository.StudentTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Service to manage request releated to StudentTestAnswer entity
 */
@Service
public class StudentTestAnswerService {
    @Autowired
    private StudentTestAnswerRepository studentTestAnswerRepository;
    @Autowired
    private StudentTestRepository studentTestRepository;
    @Autowired
    private AnswerRepository answerRepository;
    public StudentTestAnswerListDto createStudentTestAnswer(StudentTestAnswerDto studentTestAnswerDto) {
        StudentTestAnswer studentTestAnswer = this.format(studentTestAnswerDto, null);
        studentTestAnswerRepository.saveAndFlush(studentTestAnswer);
        return  convertToStudentTestAnswerList(studentTestAnswer);
    }

    public StudentTestAnswerListDto getStudentTestAnswerById(Long studentTestAnswerId)  throws NoSuchElementException{
        StudentTestAnswer studentTestAnswer =  studentTestAnswerRepository.findById(studentTestAnswerId)
                .orElseThrow();
        return convertToStudentTestAnswerList(studentTestAnswer);
    }

    public List<StudentTestAnswerListDto> getStudentTestAnswers() {
        List<StudentTestAnswer> list =  studentTestAnswerRepository.findAll();
        List<StudentTestAnswerListDto> result = new ArrayList<>();
        for(StudentTestAnswer o: list)
            result.add(convertToStudentTestAnswerList(o));
        return result;
    }

    public StudentTestAnswerListDto updateStudentTestAnswer(Long studentTestAnswerId, StudentTestAnswerDto studentTestAnswerDto) throws NoSuchElementException {
        StudentTestAnswer studentTestAnswer =  studentTestAnswerRepository.findById(studentTestAnswerId)
                .orElseThrow();
        studentTestAnswer = this.format(studentTestAnswerDto, studentTestAnswer);
        studentTestAnswerRepository.saveAndFlush(studentTestAnswer);
        return  convertToStudentTestAnswerList(studentTestAnswer);
    }

    public void deleteStudentTestAnswer(Long studentTestAnswerId) {
        studentTestRepository.deleteById(studentTestAnswerId);
    }

    public static StudentTestAnswerListDto convertToStudentTestAnswerList(StudentTestAnswer source){
        StudentTestAnswerListDto result = new StudentTestAnswerListDto();
        result.setId(source.getId());
        result.setDuration(source.getDuration());
        result.setCreationDate(source.getCreationDate());
        result.setUpdatedDate(source.getUpdatedDate());
        result.setStudentTest(StudentTestService.convertToStudentTestList(source.getStudentTest()));
        result.setAnswer(AnswerService.convertToListDto(source.getAnswer()));
       return result;
    }

    public StudentTestAnswer format(StudentTestAnswerDto studentTestAnswerDto, StudentTestAnswer studentTestAnswer ) throws NoSuchElementException {

        StudentTest studentTest =  studentTestRepository.findById(studentTestAnswerDto.getStudentTestId())
                .orElseThrow();
        Answer answer = answerRepository.findById(studentTestAnswerDto.getAnswerId())
                .orElseThrow();
        if(studentTestAnswer==null)
            studentTestAnswer = new StudentTestAnswer();

        studentTestAnswer.setStudentTest(studentTest);
        studentTestAnswer.setAnswer(answer);
        studentTestAnswer.setDuration(studentTestAnswer.getDuration());
        studentTestAnswer.setUpdatedDate(LocalDateTime.now());
        return studentTestAnswer;
    }
}
