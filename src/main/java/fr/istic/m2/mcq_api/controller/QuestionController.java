package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.dto.AnswerStatDTO;
import fr.istic.m2.mcq_api.dto.QuestionDTO;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.QuestionService;
import fr.istic.m2.mcq_api.service.statistic.QuestionStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    private final QuestionStatisticService questionStatisticService;

    QuestionController(QuestionStatisticService questionStatisticService){
        this.questionStatisticService = questionStatisticService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> browse(@PathVariable Long id){
        Question question = this.questionService.read(id);
        if (question == null){
            throw new ResourceNotFoundException("question not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(question);
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllB(){
        return ResponseEntity.status(HttpStatus.OK).body(this.questionService.getAll());
    }


    @GetMapping("/qcm/{id}")
    public ResponseEntity<List<Question>> getAllByQcm(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(this.questionService.getAllByQcm(id));
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Question> create(@RequestBody QuestionDTO questionDTO){
        Question question = this.questionService.create(questionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(question);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<Question> update(@PathVariable Long id, @RequestBody QuestionDTO questionDTO){
        Question question = this.questionService.update(id, questionDTO);
        return ResponseEntity.status(HttpStatus.OK).body(question);
    }
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        this.questionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/statistics/{id}")
    public @ResponseBody ResponseEntity<List<AnswerStatDTO>> questionStatistics(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.questionStatisticService.getQuestionStats(id));
    }
}
