package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.domain.Student;
import fr.istic.m2.mcq_api.dto.AnswerDTO;
import fr.istic.m2.mcq_api.dto.StudentDto;
import fr.istic.m2.mcq_api.dto.StudentListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("")
    public ResponseEntity<Object> getAll(){
        List<StudentListDto> objectList = this.studentService.getStudents();
        return ResponseEntity.status(HttpStatus.OK).body(objectList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentListDto> getById(@PathVariable Long id){
        StudentListDto object = this.studentService.getStudentById(id);
        if (object == null){
            throw new ResourceNotFoundException("Sthudent with id "+id + " doesn't exist !");
        }
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<StudentListDto> create(@RequestBody StudentDto studentDto){
        StudentListDto object = this.studentService.createStudent(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<StudentListDto> update(@PathVariable Long id, @RequestBody StudentDto studentDto){
        StudentListDto object = this.studentService.updateStudent(id, studentDto);
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        this.studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
