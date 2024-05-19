package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Question;
import fr.istic.m2.mcq_api.dto.QuestionDTO;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    public Question read(Long id) {
        return new Question();
    }

    public Question create(QuestionDTO questionDTO) {
        return new Question();
    }

    public Question update(Long id, QuestionDTO questionDTO) {
        return new Question();
    }

    public void delete(Long id) {
    }
}
