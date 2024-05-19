package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Teacher;
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
        Teacher teacher = new Teacher();
        teacher.setUuid(teacherDto.getUuid());
        teacher.setFirst_name(teacherDto.getFirst_name());
        teacher.setLast_name(teacherDto.getLast_name());
        Teacher result =  teacherRepository.save(teacher);
        return  convertToTeacherList(result);
    }

    public TeacherListDto getTeacherById(Long teacherId) {
        Teacher teacher =  teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));
        return convertToTeacherList(teacher);
    }

    public List<TeacherListDto> getTeachers() {
        List<Teacher> list =  teacherRepository.findAll();
        List<TeacherListDto> result = new ArrayList<>();
        for(Teacher o: list)
            result.add(convertToTeacherList(o));
        return result;
    }

    public TeacherListDto updateTeacher(Long teacherId, TeacherDto teacherDto) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));

        teacher.setUuid(teacherDto.getUuid());
        teacher.setFirst_name(teacherDto.getFirst_name());
        teacher.setLast_name(teacherDto.getLast_name());
        teacher.setUpdatedDate(LocalDateTime.now());
        Teacher result =  teacherRepository.save(teacher);
        return  convertToTeacherList(result);
    }

    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    public static TeacherListDto convertToTeacherList(Teacher source){
        TeacherListDto result = new TeacherListDto();

        result.setId(source.getId());
        result.setUuid(source.getUuid());
        result.setLast_name(source.getLast_name());
        result.setFirst_name(source.getFirst_name());
        result.setUpdatedDate(source.getUpdatedDate());
        result.setCreationDate(source.getCreationDate());
        return result;
    }
}
