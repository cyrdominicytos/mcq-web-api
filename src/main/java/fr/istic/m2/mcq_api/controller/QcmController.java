package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.dto.QcmDTO;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.QcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qcm")
public class QcmController {
    @Autowired
    private QcmService qcmService;

    @GetMapping("/{id}")
    public ResponseEntity<Qcm> browse(@PathVariable Long id){
        Qcm qcm = this.qcmService.read(id);
        if (qcm == null){
            throw new ResourceNotFoundException("qcm not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(qcm);
    }


    @PostMapping
    public @ResponseBody ResponseEntity<Qcm> create(@RequestBody QcmDTO qcmDTO){
        Qcm qcm = this.qcmService.create(qcmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(qcm);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<Qcm> update(@PathVariable Long id, @RequestBody QcmDTO qcmDTO){
        Qcm qcm = this.qcmService.update(id, qcmDTO);
        return ResponseEntity.status(HttpStatus.OK).body(qcm);
    }
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        this.qcmService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
