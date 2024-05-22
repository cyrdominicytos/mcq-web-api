package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.dto.StudentTestDto;
import fr.istic.m2.mcq_api.dto.StudentTestListDto;
import fr.istic.m2.mcq_api.dto.TeacherDto;
import fr.istic.m2.mcq_api.dto.StudentTestListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.StudentTestService;
import fr.istic.m2.mcq_api.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student_test")
public class StudentTestController {
    @Autowired
    private StudentTestService studentTestService;

    @GetMapping("")
    public ResponseEntity<Object> getAll(){
        List<StudentTestListDto> objectList = this.studentTestService.getStudentTests();
        return ResponseEntity.status(HttpStatus.OK).body(objectList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentTestListDto> getById(@PathVariable Long id){
        StudentTestListDto object = this.studentTestService.getStudentTestById(id);
        if (object == null){
            throw new ResourceNotFoundException("StudentTest with id "+id + " doesn't exist !");
        }
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<StudentTestListDto> create(@RequestBody StudentTestDto dto){
        StudentTestListDto object = this.studentTestService.createStudentTest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<StudentTestListDto> update(@PathVariable Long id, @RequestBody StudentTestDto dto){
        StudentTestListDto object = this.studentTestService.updateStudentTest(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        this.studentTestService.deleteStudentTest(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
