package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.dto.StudentTestAnswerDto;

import fr.istic.m2.mcq_api.dto.StudentTestAnswerListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.StudentTestAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student_test_answer")
public class StudentTestAnswerController {
    @Autowired
    private StudentTestAnswerService studentTestAnswerService;

    @GetMapping("")
    public ResponseEntity<Object> getAll(){
        List<StudentTestAnswerListDto> objectList = this.studentTestAnswerService.getStudentTestAnswers();
        return ResponseEntity.status(HttpStatus.OK).body(objectList);
    }
    @GetMapping("/getAllByStudentTest/{studentTestId}")
    public ResponseEntity<Object> getAllByStudentTest(@PathVariable Long studentTestId){
        List<StudentTestAnswerListDto> objectList = this.studentTestAnswerService.getAllByStudentTest(studentTestId);
        return ResponseEntity.status(HttpStatus.OK).body(objectList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentTestAnswerListDto> getById(@PathVariable Long id){
        StudentTestAnswerListDto object = this.studentTestAnswerService.getStudentTestAnswerById(id);
        if (object == null){
            throw new ResourceNotFoundException("StudentTestAnswer with id "+id + " doesn't exist !");
        }
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<StudentTestAnswerListDto> create(@RequestBody StudentTestAnswerDto dto){
        StudentTestAnswerListDto object = this.studentTestAnswerService.createStudentTestAnswer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<StudentTestAnswerListDto> update(@PathVariable Long id, @RequestBody StudentTestAnswerDto dto){
        StudentTestAnswerListDto object = this.studentTestAnswerService.updateStudentTestAnswer(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        this.studentTestAnswerService.deleteStudentTestAnswer(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
