package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.StudentTest;
import fr.istic.m2.mcq_api.domain.StudentTestAnswer;
import fr.istic.m2.mcq_api.domain.Teacher;
import fr.istic.m2.mcq_api.dto.StudentTestAnswerDto;
import fr.istic.m2.mcq_api.dto.TeacherDto;
import fr.istic.m2.mcq_api.dto.TeacherListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Service to manage request releated to Teacher entity
 */
@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    public TeacherListDto createTeacher(TeacherDto teacherDto) {
        Teacher teacher = format(teacherDto, null);
        teacherRepository.saveAndFlush(teacher);
        return  convertToTeacherList(teacher);
    }

    public TeacherListDto getTeacherById(Long teacherId) throws ResourceNotFoundException {
        Teacher teacher =  teacherRepository.findById(teacherId)
                .orElseThrow(()-> new ResourceNotFoundException("Teacher", "id", teacherId));
        return convertToTeacherList(teacher);
    }

    public List<TeacherListDto> getTeachers() {
        List<Teacher> list =  teacherRepository.findAll();
        List<TeacherListDto> result = new ArrayList<>();
        for(Teacher o: list)
            result.add(convertToTeacherList(o));
        return result;
    }

    public TeacherListDto updateTeacher(Long teacherId, TeacherDto teacherDto) throws ResourceNotFoundException {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(()-> new ResourceNotFoundException("Teacher", "id", teacherId));
        teacher = format(teacherDto, teacher);
        teacherRepository.saveAndFlush(teacher);
        return  convertToTeacherList(teacher);
    }

    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    public static TeacherListDto convertToTeacherList(Teacher source){
        TeacherListDto result = new TeacherListDto();
        result.setId(source.getId());
        result.setUuid(source.getUuid());
        result.setLastName(source.getLastName());
        result.setFirstName(source.getFirstName());
        result.setUpdatedDate(source.getUpdatedDate());
        result.setCreationDate(source.getCreationDate());
        result.setQcmCount(source.getQcmList().size());
        return result;
    }

    public Teacher format(TeacherDto teacherDto, Teacher teacher ){

        if(teacher==null)
            teacher = new Teacher();

        teacher.setUuid(teacherDto.getUuid());
        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setLastName(teacherDto.getLastName());
        teacher.setUpdatedDate(LocalDateTime.now());
       return teacher;
    }
}
