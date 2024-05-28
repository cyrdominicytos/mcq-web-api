package fr.istic.m2.mcq_api.controller;

import fr.istic.m2.mcq_api.dto.StudentTestDto;
import fr.istic.m2.mcq_api.dto.StudentTestListDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemIntController {
    @Autowired
    private SystemInitService systemInitService;


    @PostMapping
    public @ResponseBody ResponseEntity<Object> create(){
        try {
            this.systemInitService.init();
            return ResponseEntity.status(HttpStatus.OK).body("Système initialisé avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'initialisation du système "+e.getMessage());
        }
    }


}
