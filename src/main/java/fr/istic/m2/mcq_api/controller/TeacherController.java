package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.dto.StudentDto;
import fr.istic.m2.mcq_api.dto.TeacherDto;
import fr.istic.m2.mcq_api.dto.TeacherListDto;
import fr.istic.m2.mcq_api.dto.TeacherListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.StudentService;
import fr.istic.m2.mcq_api.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("")
    public ResponseEntity<Object> getAll(){
        List<TeacherListDto> objectList = this.teacherService.getTeachers();
        return ResponseEntity.status(HttpStatus.OK).body(objectList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TeacherListDto> getById(@PathVariable Long id){
        TeacherListDto object = this.teacherService.getTeacherById(id);
        if (object == null){
            throw new ResourceNotFoundException("Teacher with id "+id + " doesn't exist !");
        }
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<TeacherListDto> create(@RequestBody TeacherDto dto){
        TeacherListDto object = this.teacherService.createTeacher(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<TeacherListDto> update(@PathVariable Long id, @RequestBody TeacherDto dto){
        TeacherListDto object = this.teacherService.updateTeacher(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        this.teacherService.deleteTeacher(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
