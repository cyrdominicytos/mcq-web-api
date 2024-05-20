package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.domain.Level;
import fr.istic.m2.mcq_api.domain.Qcm;
import fr.istic.m2.mcq_api.dto.QcmDTO;
import fr.istic.m2.mcq_api.repository.LevelRepository;
import fr.istic.m2.mcq_api.repository.QcmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class QcmService {

    @Autowired
    private QcmRepository qcmRepository;
    @Autowired
    private LevelRepository levelRepository;

    public Qcm read(Long id) throws NoSuchElementException {
        return this.qcmRepository.findById(id).orElseThrow();
    }

    public Qcm create(QcmDTO qcmDTO) {
        Qcm qcm = this.formatQcm(qcmDTO, null);
        this.qcmRepository.saveAndFlush(qcm);
        return qcm;
    }

    public Qcm update(Long id, QcmDTO qcmDTO) throws NoSuchElementException {
        Qcm qcm = this.qcmRepository.findById(id).orElseThrow();
        qcm = this.formatQcm(qcmDTO, qcm);
        this.qcmRepository.saveAndFlush(qcm);
        return qcm;
    }

    public void delete(Long id) {
        this.qcmRepository.deleteById(id);
    }

    public Qcm formatQcm(QcmDTO qcmDTO, Qcm qcm) throws NoSuchElementException {
        Level level = this.levelRepository.findById(qcmDTO.getLevelId()).orElseThrow();
        if (qcm == null){
            qcm = new Qcm();
            qcm.setCreationDate(LocalDateTime.now());
        }
        qcm.setLevel(level);
        qcm.setDelay(qcmDTO.getDelay());
        qcm.setOpenStartDate(qcmDTO.getOpenStartDate());
        qcm.setCloseStartDate(qcmDTO.getCloseStartDate());
        qcm.setUpdatedDate(LocalDateTime.now());
        return qcm;
    }
}
