package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.Camara;
import com.balsagood.balsagood_app.repository.CamaraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CamaraService {

    @Autowired
    private CamaraRepository camaraRepository;

    public List<Camara> findAll() {
        return camaraRepository.findAll();
    }

    public Optional<Camara> findById(Integer id) {
        return camaraRepository.findById(id);
    }

    public Camara save(Camara camara) {
        return camaraRepository.save(camara);
    }

    @Autowired
    private com.balsagood.balsagood_app.repository.LoteSecadoRepository loteSecadoRepository;

    public void deleteById(Integer id) {
        camaraRepository.deleteById(id);
    }

    public List<Camara> findAvailable() {
        List<Integer> occupiedIds = loteSecadoRepository.findOccupiedCamaraIds();
        List<Camara> allCamaras = camaraRepository.findAll();
        if (occupiedIds.isEmpty()) {
            return allCamaras;
        }
        return allCamaras.stream()
                .filter(c -> !occupiedIds.contains(c.getIdCamara()))
                .collect(java.util.stream.Collectors.toList());
    }
}
