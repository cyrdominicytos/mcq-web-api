package fr.istic.m2.mcq_api.dto;

import fr.istic.m2.mcq_api.domain.Level;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class LevelDto {
    private String fieldOfStudy;
    private String classOfStudy;


    public Map<String, Object> toMap(Level level){
        Map<String, Object> map = new HashMap<>();
        map.put("id", level.getId());
        map.put("fieldOfStudy", level.getFieldOfStudy());
        map.put("classOfStudy", level.getClassOfStudy());
        return map;
    }
}
