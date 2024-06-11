package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.domain.Backup;
import fr.istic.m2.mcq_api.dto.BackupDto;
import fr.istic.m2.mcq_api.dto.TeacherDto;
import fr.istic.m2.mcq_api.dto.TeacherListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.BackupService;
import fr.istic.m2.mcq_api.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backups")
public class BackupController {
    @Autowired
    private BackupService backupService;

    @GetMapping("/{teacherId}")
    public ResponseEntity<Object> getByTeacher(@PathVariable Long teacherId){
        Backup o = null;
        try {
            o = this.backupService.getBackup(teacherId);
            return ResponseEntity.status(HttpStatus.OK).body(o);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Object> create(@RequestBody BackupDto dto){
        Backup object = null;
        try {
            object = this.backupService.saveBackup(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(object);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

}
