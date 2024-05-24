package fr.istic.m2.mcq_api.controller;
import fr.istic.m2.mcq_api.domain.QuestionComment;
import fr.istic.m2.mcq_api.dto.QuestionCommentDTO;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.QuestionCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments/questions")
public class QuestionCommentController {

    @Autowired
    private QuestionCommentService commentService;

    @GetMapping("/browse/{id}")
    public ResponseEntity<QuestionComment> browse(@PathVariable Long id){
        QuestionComment comment = this.commentService.read(id);
        if (comment == null){
            throw new ResourceNotFoundException("comment not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<QuestionComment>> getByQuestionId(@PathVariable Long id){
        List<QuestionComment> comment = this.commentService.getAllByQuestionId(id);
        if (comment == null){
            throw new ResourceNotFoundException("comment not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<QuestionComment> create(@RequestBody QuestionCommentDTO questionCommentDTO){
        QuestionComment comment = this.commentService.create(questionCommentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<QuestionComment> update(@PathVariable Long id, @RequestBody QuestionCommentDTO questionCommentDTO){
        QuestionComment comment = this.commentService.update(id, questionCommentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        this.commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
