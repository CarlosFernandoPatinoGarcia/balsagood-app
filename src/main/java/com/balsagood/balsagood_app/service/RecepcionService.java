package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.Recepcion;
import com.balsagood.balsagood_app.repository.RecepcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecepcionService {

    @Autowired
    private RecepcionRepository recepcionRepository;

    public List<Recepcion> findAll() {
        return recepcionRepository.findAll();
    }

    public Optional<Recepcion> findById(Integer id) {
        return recepcionRepository.findById(id);
    }

    public Recepcion save(Recepcion recepcion) {
        return recepcionRepository.save(recepcion);
    }

    public void deleteById(Integer id) {
        recepcionRepository.deleteById(id);
    }
}
