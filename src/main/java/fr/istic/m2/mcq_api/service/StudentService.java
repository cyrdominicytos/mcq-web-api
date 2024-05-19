package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Level;
import fr.istic.m2.mcq_api.domain.Student;
import fr.istic.m2.mcq_api.dto.StudentDto;
import fr.istic.m2.mcq_api.dto.StudentListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.LevelRepository;
import fr.istic.m2.mcq_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Service to manage request releated to Student entity
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LevelRepository levelRepository;
    public StudentListDto createStudent(StudentDto studentDto) {

        Level level = levelRepository.findById(studentDto.getLevel_id())
                .orElseThrow(()-> new ResourceNotFoundException("Level", "id", studentDto.getLevel_id()));
        Student student = new Student();
        student.setStudentLevel(level);
        student.setUuid(studentDto.getUuid());
        student.setFirst_name(studentDto.getFirst_name());
        student.setLast_name(studentDto.getLast_name());

        Student result =  studentRepository.save(student);
        return  convertToStudentList(result);
    }

    public StudentListDto getStudentById(Long studentId) {
        Student student =  studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        return convertToStudentList(student);
    }

    public List<StudentListDto> getStudents() {
        List<Student> list =  studentRepository.findAll();
        List<StudentListDto> result = new ArrayList<>();
        for(Student o: list)
            result.add(convertToStudentList(o));
        return result;
    }

    public StudentListDto updateStudent(Long studentId, StudentDto studentDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        Level level = levelRepository.findById(studentDto.getLevel_id())
                        .orElseThrow(()-> new ResourceNotFoundException("Level", "id", studentDto.getLevel_id()));

        student.setStudentLevel(level);
        student.setUuid(studentDto.getUuid());
        student.setFirst_name(studentDto.getFirst_name());
        student.setLast_name(studentDto.getLast_name());
        student.setUpdatedDate(LocalDateTime.now());
        Student result =  studentRepository.save(student);
        return  convertToStudentList(result);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public static StudentListDto convertToStudentList(Student source){
        StudentListDto result = new StudentListDto();

        result.setId(source.getId());
        result.setUuid(source.getUuid());
        result.setLast_name(source.getLast_name());
        result.setFirst_name(source.getFirst_name());
        result.setUpdatedDate(source.getUpdatedDate());
        result.setCreationDate(source.getCreationDate());
        result.setLevel(LevelService.convertToLevelList(source.getStudentLevel()));
        return result;
    }
}
