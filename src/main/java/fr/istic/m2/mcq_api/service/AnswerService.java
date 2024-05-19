package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Answer;
import fr.istic.m2.mcq_api.dto.AnswerDTO;
import fr.istic.m2.mcq_api.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    public Answer read(Long id) {
        return new Answer();
    }

    public Answer create(AnswerDTO answerDTO) {
        return new Answer();
    }

    public Answer update(Long id, AnswerDTO answerDTO) {
        return new Answer();
    }

    public void delete(Long id) {
    }
}
