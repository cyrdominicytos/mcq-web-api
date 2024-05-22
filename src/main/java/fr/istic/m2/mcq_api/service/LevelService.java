package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Level;
import fr.istic.m2.mcq_api.dto.LevelListDto;
import fr.istic.m2.mcq_api.dto.LevelDto;
import fr.istic.m2.mcq_api.exception.ResourceNotFoundException;
import fr.istic.m2.mcq_api.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Service to manage request releated to Level entity
 */
@Service
public class LevelService {
    @Autowired
    private LevelRepository levelRepository;
    public LevelListDto createLevel(LevelDto levelDto) {
        Level level = new Level();
        level.setClassOfStudy(levelDto.getClassOfStudy());
        level.setFieldOfStudy(levelDto.getFieldOfStudy());
        levelRepository.saveAndFlush(level);
        return  convertToLevelList(level);
    }

    public LevelListDto getLevelById(Long levelId) throws NoSuchElementException {
        Level level =  levelRepository.findById(levelId)
                .orElseThrow();
        return convertToLevelList(level);
    }

    public List<LevelListDto> getLevels() {
        List<Level> list =  levelRepository.findAll();
        List<LevelListDto> result = new ArrayList<>();
        for(Level o: list)
            result.add(convertToLevelList(o));
        return result;
    }

    public LevelListDto updateLevel(Long levelId, LevelDto levelDto) throws NoSuchElementException {
        Level level = levelRepository.findById(levelId)
                .orElseThrow();
        level.setClassOfStudy(levelDto.getClassOfStudy());
        level.setFieldOfStudy(levelDto.getFieldOfStudy());
        level.setUpdatedDate(LocalDateTime.now());
        levelRepository.saveAndFlush(level);
        return  convertToLevelList(level);
    }

    public void deleteLevel(Long levelId) {
        levelRepository.deleteById(levelId);
    }
    public static LevelListDto convertToLevelList(Level source){
        LevelListDto result = new LevelListDto();
       result.setId(source.getId());
       result.setClassOfStudy(source.getClassOfStudy());
       result.setFieldOfStudy(source.getFieldOfStudy());
       result.setCreationDate(source.getCreationDate());
       result.setUpdatedDate(source.getUpdatedDate());
       result.setQcmCount(source.getQcmList().size());
       result.setStudentCount(source.getStudentList().size());
       return result;
    }
}
