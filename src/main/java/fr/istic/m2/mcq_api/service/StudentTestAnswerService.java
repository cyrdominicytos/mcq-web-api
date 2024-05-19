package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.StudentTest;
import fr.istic.m2.mcq_api.domain.StudentTestAnswer;
import fr.istic.m2.mcq_api.dto.StudentTestAnswerDto;
import fr.istic.m2.mcq_api.dto.StudentTestAnswerListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.StudentTestAnswerRepository;
import fr.istic.m2.mcq_api.repository.StudentTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public StudentTestAnswerListDto createStudentTestAnswer(StudentTestAnswerDto studentTestAnswerDto) {
        StudentTest studentTest =  studentTestRepository.findById(studentTestAnswerDto.getStudent_test_id())
                .orElseThrow(() -> new ResourceNotFoundException("StudentTest", "id", studentTestAnswerDto.getStudent_test_id()));
        //TODO  : Add answer checking

        StudentTestAnswer studentTestAnswer = new StudentTestAnswer();
        studentTestAnswer.setStudentTest(studentTest);
        //TODO :  answer to  studentTestAnswer
        studentTestAnswer.setDuration(studentTestAnswer.getDuration());
        StudentTestAnswer result =  studentTestAnswerRepository.save(studentTestAnswer);
        return  convertToStudentTestAnswerList(result);
    }

    public StudentTestAnswerListDto getStudentTestAnswerById(Long studentTestAnswerId) {
        StudentTestAnswer studentTestAnswer =  studentTestAnswerRepository.findById(studentTestAnswerId)
                .orElseThrow(() -> new ResourceNotFoundException("StudentTestAnswer", "id", studentTestAnswerId));
        return convertToStudentTestAnswerList(studentTestAnswer);
    }

    public List<StudentTestAnswerListDto> getStudentTestAnswers() {
        List<StudentTestAnswer> list =  studentTestAnswerRepository.findAll();
        List<StudentTestAnswerListDto> result = new ArrayList<>();
        for(StudentTestAnswer o: list)
            result.add(convertToStudentTestAnswerList(o));
        return result;
    }

    public StudentTestAnswerListDto updateStudentTestAnswer(Long studentTestAnswerId, StudentTestAnswerDto studentTestAnswerDto) {
        StudentTestAnswer studentTestAnswer =  studentTestAnswerRepository.findById(studentTestAnswerId)
                .orElseThrow(() -> new ResourceNotFoundException("StudentTestAnswer", "id", studentTestAnswerId));

        StudentTest studentTest =  studentTestRepository.findById(studentTestAnswerDto.getStudent_test_id())
                .orElseThrow(() -> new ResourceNotFoundException("StudentTest", "id", studentTestAnswerDto.getStudent_test_id()));

        //TODO  : Add answer checking

        studentTestAnswer.setStudentTest(studentTest);
        //TODO :  answer to  studentTestAnswer
        studentTestAnswer.setDuration(studentTestAnswer.getDuration());
        studentTestAnswer.setUpdatedDate(LocalDateTime.now());
        StudentTestAnswer result =  studentTestAnswerRepository.save(studentTestAnswer);
        return  convertToStudentTestAnswerList(result);
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
        //TODO : ADD Answer to the result
       return result;
    }
}
