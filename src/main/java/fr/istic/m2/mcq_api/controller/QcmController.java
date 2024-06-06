package fr.istic.m2.mcq_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.dto.*;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.parser.ParsingQuestion;
import fr.istic.m2.mcq_api.service.QcmService;
import fr.istic.m2.mcq_api.service.ScoreService;
import fr.istic.m2.mcq_api.service.statistic.QuestionStatisticService;
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
import java.util.Set;

@RestController
@RequestMapping("/qcm")
public class QcmController {
    @Autowired
    private QcmService qcmService;
    @Autowired
    private ScoreService scoreService;

    private final QuestionStatisticService statsService;

    @Autowired
    public QcmController(QuestionStatisticService statsService){
        this.statsService = statsService;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<Qcm>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.qcmService.getAll());
    }

    @GetMapping("/teacher/{id}")
    public @ResponseBody ResponseEntity<List<Qcm>> getAllByTeacherId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.qcmService.getAllTeacherId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> browse(@PathVariable Long id){
        Qcm qcm = this.qcmService.read(id);
        if (qcm == null){
            throw new ResourceNotFoundException("qcm not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(qcm);
    }


    @PostMapping
    public @ResponseBody ResponseEntity<Object> create(@RequestBody QcmDTO qcmDTO){
        Qcm qcm = this.qcmService.create(qcmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(qcm);
    }

    /**
     * This fonction is using custom parser
     * @param file
     * @param levelId
     * @param teacherId
     * @param limitQuestion
     * @param delay
     * @param title
     * @param complexity
     * @param isRandomActive
     * @param isActive
     * @param openStartDate
     * @param closeStartDate
     * @return
     */
    @PostMapping("/createQCMFromString")
    public @ResponseBody ResponseEntity<Object> createQCMFromString(@RequestParam("file") MultipartFile file,
          @RequestParam Long levelId,
          @RequestParam Long teacherId,
          @RequestParam int limitQuestion,
          @RequestParam int delay,
          @RequestParam String title,
          @RequestParam int complexity,
          @RequestParam boolean isRandomActive,
          @RequestParam boolean isActive,
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime openStartDate,
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime closeStartDate
    ){
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
        QcmWithTextDTO dto = new QcmWithTextDTO();
        dto.setDto(qcmRequest);
        List<Question> qcmList = null;
        try {
            String content = new String(file.getBytes());
            dto.setText(content);
            qcmList = this.qcmService.defaultParseQCMText(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(qcmList.size());
    }
@PutMapping("/updateQCMFromString/{qcmId}")
    public @ResponseBody ResponseEntity<Object> updateQCMFromString(
          @PathVariable Long qcmId,
          @RequestParam("file") MultipartFile file,
          @RequestParam Long levelId,
          @RequestParam Long teacherId,
          @RequestParam int limitQuestion,
          @RequestParam int delay,
          @RequestParam String title,
          @RequestParam int complexity,
          @RequestParam boolean isRandomActive,
          @RequestParam boolean isActive,
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime openStartDate,
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime closeStartDate
    ){
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
        QcmWithTextDTO dto = new QcmWithTextDTO();
        dto.setDto(qcmRequest);
        Qcm qcmList = null;
        try {
            String content = new String(file.getBytes());
            dto.setText(content);
            qcmList = this.qcmService.defaultParseQCMTextToUpdateQCM(qcmId, dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("QCM mise à jour avec succès !");
    }

    @PutMapping("/getStringFormatOfQCM/{qcmId}/{teacherId}")
    public  @ResponseBody ResponseEntity<Object> getStringFormatOfQCM(@PathVariable Long qcmId, @PathVariable Long teacherId){
        try {
            QcmToTextDTO data = qcmService.retriveQcmForEdition(qcmId, teacherId);
            return ResponseEntity.status(HttpStatus.OK).body(data);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/createQCMFromYaml")
    public @ResponseBody ResponseEntity<Object> createQCMFromYaml(
        @RequestParam("file") MultipartFile file,
        @RequestParam Long levelId,
        @RequestParam Long teacherId,
        @RequestParam int limitQuestion,
        @RequestParam int delay,
        @RequestParam String title,
        @RequestParam int complexity,
        @RequestParam boolean isRandomActive,
        @RequestParam boolean isActive,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime openStartDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime closeStartDate
    ){
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
    @PostMapping("/updateQCMFromYaml")
    public @ResponseBody ResponseEntity<Object> updateQCMFromYaml(
        @RequestParam("file") MultipartFile file,
        @RequestParam Long qcmId,
        @RequestParam Long levelId,
        @RequestParam Long teacherId,
        @RequestParam int limitQuestion,
        @RequestParam int delay,
        @RequestParam String title,
        @RequestParam int complexity,
        @RequestParam boolean isRandomActive,
        @RequestParam boolean isActive,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime openStartDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime closeStartDate
    ){
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
           QcmListDTO qcmList = this.qcmService.updateQcmFromYaml(qcmId,yamlContent, qcmRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(qcmList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format invalid : "+e.getMessage());
        }
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
            Qcm qcmListDTO = qcmService.createQCMFromJSON(json, dto);
            return ResponseEntity.status(HttpStatus.OK).body(qcmListDTO);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to parse JSON: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadQuestions(@RequestParam("file") MultipartFile file) {
        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            List<ParsingQuestion> questions = qcmService.parseQuestionsFromText(content);
            System.out.println("taille: " + questions.size());
            questions.forEach(question -> {
                System.out.println("Question: " + question.getTitle());
                question.getMetaData().forEach(meta ->
                        System.out.println("Meta - " + meta.getType() + ": " + meta.getValue()));
                question.getOptions().forEach(option ->
                        System.out.println("Option " + option.getType() + ": " + option.getText()));
            });
            return ResponseEntity.ok(questions);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File reading error");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid file format");
        }
    }

    @GetMapping("/statistics/{id}")
    public ResponseEntity<QcmStatDTO> getStatistic(@PathVariable Long id){
        return  ResponseEntity.status(HttpStatus.OK).body(this.scoreService.getQcmStats(id));
    }

}
