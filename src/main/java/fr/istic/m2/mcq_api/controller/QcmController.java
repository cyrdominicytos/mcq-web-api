package fr.istic.m2.mcq_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.dto.*;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.QcmService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/qcm")
public class QcmController {
    @Autowired
    private QcmService qcmService;


    @GetMapping
    public @ResponseBody ResponseEntity<List<Qcm>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.qcmService.getAll());
    }

    @GetMapping("/teacher/{id}")
    public @ResponseBody ResponseEntity<List<Qcm>> getAllByTeacherId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.qcmService.getAllTeacherId(id));
    }

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

    @PostMapping("/createQCMFromString")
    public @ResponseBody ResponseEntity<Object> createQCMFromString(@RequestBody QcmWithTextDTO qcmDTO){
        List<Question> qcmList = this.qcmService.parseQCM(qcmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(qcmList.size());
    }
    @PostMapping("/createQCMFromYamlString")
    public @ResponseBody ResponseEntity<Object> createQCMFromYamlString(@RequestParam("file") MultipartFile file,
                                                                        @RequestParam Long levelId,
                                                                        @RequestParam Long teacherId,
                                                                        @RequestParam int limitQuestion,
                                                                        @RequestParam int delay,
                                                                        @RequestParam String title,
                                                                        @RequestParam int complexity,
                                                                        @RequestParam boolean isRandomActive,
                                                                        @RequestParam boolean isActive,
                                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime openStartDate,
                                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime closeStartDate){
        QcmDTO qcmRequest = new QcmDTO();
        qcmRequest.setLevelId(levelId);
        qcmRequest.setTeacherId(teacherId);
        qcmRequest.setLimitQuestion(limitQuestion);
        qcmRequest.setDelay(delay);
        qcmRequest.setTitle(title);
        qcmRequest.setComplexity(complexity);
        qcmRequest.setRandomActive(isRandomActive);
        qcmRequest.setActive(isActive);
        qcmRequest.setOpenStartDate(openStartDate);
        qcmRequest.setCloseStartDate(closeStartDate);

        try {
            String yamlContent = new String(file.getBytes());
           QcmListDTO qcmList = this.qcmService.createQCMFromYaml(yamlContent, qcmRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(qcmList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format invalid : "+e.getMessage());
        }
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

    @GetMapping("/{id}/export")
    @ResponseStatus(HttpStatus.OK)
    public void exportQcmAsJson(@PathVariable Long id, HttpServletResponse response) {
        try {
            qcmService.exportQCMInJSON(id, response);
        } catch (ResourceNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            try {
                response.getWriter().write("Resource not found: " + e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }
    }

    @PostMapping("/createQcmFromJson/{teacherId}/{levelId}")
    public ResponseEntity<Object> createQcmViaJson(@RequestParam("file") MultipartFile file,@PathVariable Long teacherId, @PathVariable Long levelId) {
        try {
            // Lire le contenu du fichier
            String json = new String(file.getBytes(), StandardCharsets.UTF_8);
            // Parser le contenu JSON
            QcmCreateJSONDto dto = new QcmCreateJSONDto();
            dto.setTeacherId(teacherId);
            dto.setLevelId(levelId);
            QcmListDTO qcmListDTO = qcmService.createQCMFromJSON(json, dto);
            return ResponseEntity.status(HttpStatus.OK).body(qcmListDTO);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to parse JSON: " + e.getMessage());
        }
    }

}
