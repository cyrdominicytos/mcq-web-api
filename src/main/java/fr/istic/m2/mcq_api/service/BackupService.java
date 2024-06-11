package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Backup;
import fr.istic.m2.mcq_api.domain.Teacher;
import fr.istic.m2.mcq_api.dto.BackupDto;
import fr.istic.m2.mcq_api.dto.TeacherDto;
import fr.istic.m2.mcq_api.dto.TeacherListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.BackupRepository;
import fr.istic.m2.mcq_api.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Service to manage request releated to Backup entity
 */
@Service
public class BackupService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private BackupRepository backupRepository;
    public Backup saveBackup(BackupDto dto) throws Exception {
        Teacher teacher =  teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(()-> new ResourceNotFoundException("Teacher", "id",dto.getTeacherId()));
        Backup backup = backupRepository.findBackupByTeacherId(dto.getTeacherId());
        if(backup == null){
            backup = new Backup();
            backup.setTeacher(teacher);
            backup.setCreationDate(LocalDateTime.now());
        }else{
            backup.setUpdatedDate(LocalDateTime.now());
        }
        backup.setContent(dto.getContent());
        return backupRepository.saveAndFlush(backup);
    }
    public Backup getBackup(Long teacherId) throws  Exception {
        Teacher teacher =  teacherRepository.findById(teacherId)
                .orElseThrow(()-> new ResourceNotFoundException("Teacher", "id",teacherId));
        return backupRepository.findBackupByTeacherId(teacherId);
    }
}
