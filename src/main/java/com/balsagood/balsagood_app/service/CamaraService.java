package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.dto.dashboard.CamaraDTO;
import com.balsagood.balsagood_app.model.Camara;
import com.balsagood.balsagood_app.repository.CamaraRepository;
import com.balsagood.balsagood_app.repository.LoteSecadoRepository;
import com.balsagood.balsagood_app.util.AppMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CamaraService {

    @Autowired
    private CamaraRepository camaraRepository;

    public List<Camara> findAll() {
        return camaraRepository.findByCamaraEstado('A');
    }

    public Optional<Camara> findById(Integer id) {
        return camaraRepository.findById(id);
    }

    public Camara save(Camara camara) {
        return camaraRepository.save(camara);
    }

    @Autowired
    private LoteSecadoRepository loteSecadoRepository;

    public void deleteById(Integer id) {
        camaraRepository.deleteById(id);
    }

    public void softDelete(Integer id) {
        camaraRepository.findById(id).ifPresent(camara -> {
            camara.setCamaraEstado('E');
            camaraRepository.save(camara);
        });
    }

    public List<Camara> findAvailable() {
        List<Integer> occupiedIds = loteSecadoRepository.findOccupiedCamaraIds();
        List<Camara> allCamaras = findAll();
        if (occupiedIds.isEmpty()) {
            return allCamaras;
        }
        return allCamaras.stream()
                .filter(c -> !occupiedIds.contains(c.getIdCamara()))
                .collect(Collectors.toList());
    }

    @Autowired
    private AppMapper appMapper;

    public List<CamaraDTO> getCamarasConDisponibilidad() {
        return findAll().stream()
                .map(appMapper::toCamaraDTO)
                .collect(Collectors.toList());
    }
}
