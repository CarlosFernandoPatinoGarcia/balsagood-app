package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.TipoMadera;
import com.balsagood.balsagood_app.repository.TipoMaderaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoMaderaService {

    @Autowired
    private TipoMaderaRepository tipoMaderaRepository;

    public List<TipoMadera> findAll() {
        return tipoMaderaRepository.findAll();
    }

    public Optional<TipoMadera> findById(Integer id) {
        return tipoMaderaRepository.findById(id);
    }
}
