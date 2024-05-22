package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.dto.LevelDto;
import fr.istic.m2.mcq_api.dto.LevelListDto;
import fr.istic.m2.mcq_api.dto.TeacherDto;
import fr.istic.m2.mcq_api.dto.LevelListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.LevelService;
import fr.istic.m2.mcq_api.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/levels")
public class LevelController {
    @Autowired
    private LevelService levelService;

    @GetMapping("")
    public ResponseEntity<Object> getAll(){
        List<LevelListDto> objectList = this.levelService.getLevels();
        return ResponseEntity.status(HttpStatus.OK).body(objectList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<LevelListDto> getById(@PathVariable Long id){
        LevelListDto object = this.levelService.getLevelById(id);
        if (object == null){
            throw new ResourceNotFoundException("Level with id "+id + " doesn't exist !");
        }
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<LevelListDto> create(@RequestBody LevelDto dto){
        LevelListDto object = this.levelService.createLevel(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<LevelListDto> update(@PathVariable Long id, @RequestBody LevelDto dto){
        LevelListDto object = this.levelService.updateLevel(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        this.levelService.deleteLevel(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
