package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.Bloque;
import com.balsagood.balsagood_app.repository.BloqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BloqueService {

    @Autowired
    private BloqueRepository bloqueRepository;

    public List<Bloque> findAll() {
        return bloqueRepository.findAll();
    }

    public Optional<Bloque> findById(Integer id) {
        return bloqueRepository.findById(id);
    }

    public Bloque save(Bloque bloque) {
        return bloqueRepository.save(bloque);
    }

    public void deleteById(Integer id) {
        bloqueRepository.deleteById(id);
    }
}
