package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Level;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.StudentTest;
import fr.istic.m2.mcq_api.domain.Teacher;
import fr.istic.m2.mcq_api.dto.QcmDTO;
import fr.istic.m2.mcq_api.dto.QcmListDTO;
import fr.istic.m2.mcq_api.dto.StudentTestListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.LevelRepository;
import fr.istic.m2.mcq_api.repository.QcmRepository;
import fr.istic.m2.mcq_api.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QcmService {

    @Autowired
    private QcmRepository qcmRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    public QcmListDTO read(Long id) throws ResourceNotFoundException {
        Qcm qcm =   this.qcmRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Qcm", "id", id));
        return  convertToListDto(qcm);
    }

    public QcmListDTO create(QcmDTO qcmDTO) {
        Qcm qcm = this.formatQcm(qcmDTO, null);
        this.qcmRepository.saveAndFlush(qcm);
        return convertToListDto(qcm);
    }

    public QcmListDTO update(Long id, QcmDTO qcmDTO) throws ResourceNotFoundException {
        Qcm qcm = this.qcmRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Qcm", "id", id));
        qcm = this.formatQcm(qcmDTO, qcm);
        this.qcmRepository.saveAndFlush(qcm);
        return convertToListDto(qcm) ;
    }

    public void delete(Long id) {
        this.qcmRepository.deleteById(id);
    }

    public Qcm formatQcm(QcmDTO qcmDTO, Qcm qcm) throws ResourceNotFoundException {
        Level level = this.levelRepository.findById(qcmDTO.getLevelId()).orElseThrow(()-> new ResourceNotFoundException("Level", "id", qcmDTO.getLevelId()));
        Teacher teacher = this.teacherRepository.findById(qcmDTO.getTeacherId()).orElseThrow(()-> new ResourceNotFoundException("Teacher", "id", qcmDTO.getTeacherId()));
        if (qcm == null){
            qcm = new Qcm();
            qcm.setCreationDate(LocalDateTime.now());
        }
        qcm.setLevel(level);
        qcm.setTeacher(teacher);
        qcm.setDelay(qcmDTO.getDelay());
        qcm.setComplexity(qcm.getComplexity());
        qcm.setRandomActive(qcm.isRandomActive());
        qcm.setTitle(qcmDTO.getTitle());
        qcm.setOpenStartDate(qcmDTO.getOpenStartDate());
        qcm.setCloseStartDate(qcmDTO.getCloseStartDate());
        qcm.setUpdatedDate(LocalDateTime.now());
        return qcm;
    }

    public static QcmListDTO convertToListDto(Qcm source){
        QcmListDTO result = new QcmListDTO();
        result.setId(source.getId());
        result.setActive(source.isActive());
        result.setDelay(source.getDelay());
        result.setLimitQuestion(source.getLimitQuestion());
        result.setOpenStartDate(source.getOpenStartDate());
        result.setCloseStartDate(source.getCloseStartDate());
        result.setQuestionCount(source.getQuestions().size());
        result.setTestCount(source.getStudentTestList().size());
        result.setCreationDate(source.getCreationDate());
        result.setUpdatedDate(source.getUpdatedDate());
        result.setTeacher(TeacherService.convertToTeacherList(source.getTeacher()));

        result.setRandomActive(source.isRandomActive());
        result.setTitle(source.getTitle());
        result.setComplexity(source.getComplexity());
        return result;
    }

    public List<Qcm> getAll() {
        return this.qcmRepository.findAll();
    }

    public List<Qcm> getAllTeacherId(Long id) {
        return this.qcmRepository.findAllByTeacherId(id);
    }
}
