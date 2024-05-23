package fr.istic.m2.mcq_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.dto.QcmCreateJSONDto;
import fr.istic.m2.mcq_api.dto.QcmDTO;
import fr.istic.m2.mcq_api.dto.QcmJSONDTO;
import fr.istic.m2.mcq_api.dto.QcmListDTO;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.QcmService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    @PostMapping("/parse")
    public @ResponseBody ResponseEntity<Object> parseQCM(@RequestBody String text, @RequestBody QcmDTO qcmDTO){
        List<Question> qcmList = this.qcmService.parseQCM(text, qcmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(qcmList.size());
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
