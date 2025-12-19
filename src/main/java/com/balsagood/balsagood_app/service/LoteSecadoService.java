package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.LoteSecado;
import com.balsagood.balsagood_app.repository.LoteSecadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LoteSecadoService {

    @Autowired
    private LoteSecadoRepository loteSecadoRepository;

    public List<LoteSecado> findAll() {
        return loteSecadoRepository.findAll();
    }

    public Optional<LoteSecado> findById(Integer id) {
        return loteSecadoRepository.findById(id);
    }

    public LoteSecado save(LoteSecado loteSecado) {
        return loteSecadoRepository.save(loteSecado);
    }

    public void deleteById(Integer id) {
        loteSecadoRepository.deleteById(id);
    }
}
