package fr.istic.m2.mcq_api.controller;


import fr.istic.m2.mcq_api.domain.Student;
import fr.istic.m2.mcq_api.dto.StudentDto;
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

    @PostMapping
    public ResponseEntity<Object> createStudent(@RequestBody StudentDto studentDto) {
        Student student = new Student();

        student.setLast_name(studentDto.getLast_name());
        student.setFirst_name(studentDto.getFirst_name());
        student.set

        Student createdStudent = userService.createStudent(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getStudentById(@PathVariable Long userId) {
        try{
            Student user = userService.getStudentById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'utilisateur avec l'identifiant "+userId+" n'existe pas !");
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> getStudents() {
        List<Student> users = userService.getStudents();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long userId, @RequestBody StudentDto userDetails) {
        Student updatedStudent = userService.updateStudent(userId, userDetails);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long userId) {
        userService.deleteStudent(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

