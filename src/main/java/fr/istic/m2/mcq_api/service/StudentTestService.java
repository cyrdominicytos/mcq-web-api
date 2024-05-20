package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Level;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Student;
import fr.istic.m2.mcq_api.domain.StudentTest;
import fr.istic.m2.mcq_api.dto.QcmDTO;
import fr.istic.m2.mcq_api.dto.QcmListDTO;
import fr.istic.m2.mcq_api.dto.StudentTestDto;
import fr.istic.m2.mcq_api.dto.StudentTestListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.LevelRepository;
import fr.istic.m2.mcq_api.repository.QcmRepository;
import fr.istic.m2.mcq_api.repository.StudentRepository;
import fr.istic.m2.mcq_api.repository.StudentTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    @Autowired
    private QcmRepository qcmRepository;
    public StudentTestListDto createStudentTest(StudentTestDto studentTestDto) {
        StudentTest studentTest  = this.format(studentTestDto, null);
        studentTestRepository.saveAndFlush(studentTest);
        return  convertToStudentTestList(studentTest);
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
        StudentTest studentTest = studentTestRepository.findById(studentTestId).orElseThrow(() -> new ResourceNotFoundException("StudentTest", "id", studentTestId));
        studentTest = this.format(studentTestDto, studentTest);
        studentTestRepository.saveAndFlush(studentTest);
        return  convertToStudentTestList(studentTest);
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
        result.setQcm(QcmService.convertToListDto(source.getQcm()));
       return result;
    }

    public StudentTest format(StudentTestDto studentTestDto, StudentTest studentTest ) throws ResourceNotFoundException {
        Student student =  studentRepository.findById(studentTestDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentTestDto.getStudentId()));

        Qcm qcm = qcmRepository.findById(studentTestDto.getQcmId()).orElseThrow(() -> new ResourceNotFoundException("Qcm", "id", studentTestDto.getQcmId()));
        if(studentTest==null){
             studentTest = new StudentTest();
        }
        studentTest.setStudent(student);
        studentTest.setQcm(qcm);
        studentTest.setStartingDate(studentTestDto.getStartingDate());
        studentTest.setEndDate(studentTestDto.getEndDate());
        studentTest.setUpdatedDate(LocalDateTime.now());
        return studentTest;
    }



}
