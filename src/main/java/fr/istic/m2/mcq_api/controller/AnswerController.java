package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.dto.AnswerDTO;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping("/{id}")
    public ResponseEntity<Answer> browse(@PathVariable Long id){
        Answer answer = this.answerService.read(id);
        if (answer == null){
            throw new ResourceNotFoundException("answer not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Answer> create(@RequestBody AnswerDTO answerDTO){
        Answer answer = this.answerService.create(answerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(answer);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<Answer> update(@PathVariable Long id, @RequestBody AnswerDTO answerDTO){
        Answer answer = this.answerService.update(id, answerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        this.answerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}