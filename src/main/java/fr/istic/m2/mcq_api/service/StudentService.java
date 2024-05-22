package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Level;
import fr.istic.m2.mcq_api.domain.Student;
import fr.istic.m2.mcq_api.domain.Teacher;
import fr.istic.m2.mcq_api.dto.StudentDto;
import fr.istic.m2.mcq_api.dto.StudentListDto;
import fr.istic.m2.mcq_api.dto.TeacherDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.LevelRepository;
import fr.istic.m2.mcq_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    public StudentListDto createStudent(StudentDto studentDto)  throws NoSuchElementException {
        Student student = this.format(studentDto, null);
        studentRepository.saveAndFlush(student);
        return  convertToStudentList(student);
    }

    public StudentListDto getStudentById(Long studentId)  throws NoSuchElementException{
        Student student =  studentRepository.findById(studentId)
                .orElseThrow();
        return convertToStudentList(student);
    }

    public List<StudentListDto> getStudents() {
        List<Student> list =  studentRepository.findAll();
        List<StudentListDto> result = new ArrayList<>();
        for(Student o: list)
            result.add(convertToStudentList(o));
        return result;
    }

    public StudentListDto updateStudent(Long studentId, StudentDto studentDto)  throws NoSuchElementException{
        Student student = studentRepository.findById(studentId)
                .orElseThrow();
        student = this.format(studentDto, student);
        studentRepository.saveAndFlush(student);
        return  convertToStudentList(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public static StudentListDto convertToStudentList(Student source){
        StudentListDto result = new StudentListDto();

        result.setId(source.getId());
        result.setUuid(source.getUuid());
        result.setLastName(source.getLastName());
        result.setFirstName(source.getFirstName());
        result.setUpdatedDate(source.getUpdatedDate());
        result.setCreationDate(source.getCreationDate());
        result.setLevel(LevelService.convertToLevelList(source.getStudentLevel()));
        return result;
    }

    public Student format(StudentDto studentDto, Student student ) throws NoSuchElementException{
        Level level = levelRepository.findById(studentDto.getLevelId())
                .orElseThrow();
        if(student==null)
            student = new Student();

        student.setUuid(studentDto.getUuid());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setStudentLevel(level);
        student.setUpdatedDate(LocalDateTime.now());
        return student;
    }
}
