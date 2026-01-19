package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.HistorialPalletsSecando;
import com.balsagood.balsagood_app.repository.HistorialPalletsSecandoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialPalletsSecandoService {

    @Autowired
    private HistorialPalletsSecandoRepository repository;

    public List<HistorialPalletsSecando> findAll() {
        return repository.findAll();
    }

    public Optional<HistorialPalletsSecando> findById(Long id) {
        return repository.findById(id);
    }

    public HistorialPalletsSecando save(HistorialPalletsSecando historial) {
        return repository.save(historial);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
