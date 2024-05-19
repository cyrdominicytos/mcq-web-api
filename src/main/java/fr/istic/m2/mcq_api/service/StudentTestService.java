package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Student;
import fr.istic.m2.mcq_api.domain.StudentTest;
import fr.istic.m2.mcq_api.dto.StudentTestDto;
import fr.istic.m2.mcq_api.dto.StudentTestListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.LevelRepository;
import fr.istic.m2.mcq_api.repository.StudentRepository;
import fr.istic.m2.mcq_api.repository.StudentTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Service to manage request releated to StudentTest entity
 */
@Service
public class StudentTestService {
    @Autowired
    private StudentTestRepository studentTestRepository;
    @Autowired
    private StudentRepository studentRepository;
    public StudentTestListDto createStudentTest(StudentTestDto studentTestDto) {
        Student student =  studentRepository.findById(studentTestDto.getStudent_id())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentTestDto.getStudent_id()));
        //TODO  : Add qcm checking


        StudentTest studentTest = new StudentTest();
        studentTest.setStudent(student);
        //TODO :  qcm to  studentTest
        studentTest.setStartingDate(studentTest.getStartingDate());
        studentTest.setEndDate(studentTest.getEndDate());
        StudentTest result =  studentTestRepository.save(studentTest);
        return  convertToStudentTestList(result);
    }

    public StudentTestListDto getStudentTestById(Long studentTestId) {
        StudentTest studentTest =  studentTestRepository.findById(studentTestId)
                .orElseThrow(() -> new ResourceNotFoundException("StudentTest", "id", studentTestId));
        return convertToStudentTestList(studentTest);
    }

    public List<StudentTestListDto> getStudentTests() {
        List<StudentTest> list =  studentTestRepository.findAll();
        List<StudentTestListDto> result = new ArrayList<>();
        for(StudentTest o: list)
            result.add(convertToStudentTestList(o));
        return result;
    }

    public StudentTestListDto updateStudentTest(Long studentTestId, StudentTestDto studentTestDto) {
        StudentTest studentTest = studentTestRepository.findById(studentTestId)
                .orElseThrow(() -> new ResourceNotFoundException("StudentTest", "id", studentTestId));

        Student student =  studentRepository.findById(studentTestDto.getStudent_id())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentTestDto.getStudent_id()));
        //TODO  : Add qcm checking
        studentTest.setStudent(student);
        //TODO :  qcm to  studentTest
        studentTest.setStartingDate(studentTest.getStartingDate());
        studentTest.setEndDate(studentTest.getEndDate());
        studentTest.setUpdatedDate(LocalDateTime.now());
        StudentTest result =  studentTestRepository.save(studentTest);
        return  convertToStudentTestList(result);
    }

    public void deleteStudentTest(Long studentTestId) {
        studentTestRepository.deleteById(studentTestId);
    }
    public static StudentTestListDto convertToStudentTestList(StudentTest source){
        StudentTestListDto result = new StudentTestListDto();
        result.setId(source.getId());
        result.setStartingDate(source.getStartingDate());
        result.setEndDate(source.getEndDate());
        result.setCreationDate(source.getCreationDate());
        result.setUpdatedDate(source.getUpdatedDate());
        result.setStudent(StudentService.convertToStudentList(source.getStudent()));
       return result;
    }
}
