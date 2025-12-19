package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.Cuerpo;
import com.balsagood.balsagood_app.repository.CuerpoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CuerpoService {

    @Autowired
    private CuerpoRepository cuerpoRepository;

    public List<Cuerpo> findAll() {
        return cuerpoRepository.findAll();
    }

    public Optional<Cuerpo> findById(Integer id) {
        return cuerpoRepository.findById(id);
    }

    public Cuerpo save(Cuerpo cuerpo) {
        return cuerpoRepository.save(cuerpo);
    }

    public void deleteById(Integer id) {
        cuerpoRepository.deleteById(id);
    }
}
