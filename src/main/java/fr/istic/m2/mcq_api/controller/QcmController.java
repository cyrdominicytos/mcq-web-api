package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.dto.QcmDTO;
import fr.istic.m2.mcq_api.dto.QcmListDTO;
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
    public ResponseEntity<QcmListDTO> browse(@PathVariable Long id){
        QcmListDTO qcm = this.qcmService.read(id);
        if (qcm == null){
            throw new ResourceNotFoundException("qcm not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(qcm);
    }


    @PostMapping
    public @ResponseBody ResponseEntity<QcmListDTO> create(@RequestBody QcmDTO qcmDTO){
        QcmListDTO qcm = this.qcmService.create(qcmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(qcm);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody ResponseEntity<QcmListDTO> update(@PathVariable Long id, @RequestBody QcmDTO qcmDTO){
        QcmListDTO qcm = this.qcmService.update(id, qcmDTO);
        return ResponseEntity.status(HttpStatus.OK).body(qcm);
    }
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable Long id){
        this.qcmService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
