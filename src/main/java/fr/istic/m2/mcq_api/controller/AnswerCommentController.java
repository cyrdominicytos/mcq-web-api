package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.domain.AnswerComment;
import fr.istic.m2.mcq_api.dto.AnswerCommentDTO;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.AnswerCommentService;
import fr.istic.m2.mcq_api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;

@RestController
@RequestMapping("/comments/answers")
public class AnswerCommentController {

    @Autowired
    private AnswerCommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<AnswerComment> browse(@PathVariable Long id){
        AnswerComment comment = this.commentService.read(id);
        if (comment == null){
            throw new ResourceNotFoundException("User not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<AnswerComment> create(@RequestBody AnswerCommentDTO answerCommentDTO){
        AnswerComment comment = this.commentService.create(answerCommentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PostMapping("/update/{id}")
    public @ResponseBody ResponseEntity<AnswerComment> update(@PathVariable Long id, @RequestBody AnswerCommentDTO answerCommentDTO){
        AnswerComment comment = this.commentService.update(id, answerCommentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
