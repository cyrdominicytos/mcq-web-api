package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.domain.AnswerComment;
import fr.istic.m2.mcq_api.dto.AnswerCommentDTO;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.AnswerCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.List;

@RestController
@RequestMapping("/comments/answers")
public class AnswerCommentController {

    @Autowired
    private AnswerCommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<List<AnswerComment>> getAllByAnswer(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getAllByAnswerId(id));
    }

    @GetMapping
    public ResponseEntity<List<AnswerComment>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getAll());
    }


    @PostMapping
    public @ResponseBody ResponseEntity<AnswerComment> create(@RequestBody AnswerCommentDTO answerCommentDTO){
        AnswerComment comment = this.commentService.create(answerCommentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<AnswerComment> update(@PathVariable Long id, @RequestBody AnswerCommentDTO answerCommentDTO){
        AnswerComment comment = this.commentService.update(id, answerCommentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        this.commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
