package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.Bloque;
import com.balsagood.balsagood_app.repository.BloqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BloqueService {

    @Autowired
    private BloqueRepository bloqueRepository;

    @Autowired
    private com.balsagood.balsagood_app.repository.OrdenTallerRepository ordenTallerRepository;

    public List<Bloque> findAll() {
        return bloqueRepository.findAll();
    }

    public Optional<Bloque> findById(Integer id) {
        return bloqueRepository.findById(id);
    }

    public Bloque save(Bloque bloque) {
        // Validation for new blocks
        if (bloque.getIdBloque() == null) {
            if (bloque.getEstado() == null) {
                bloque.setEstado("PRESENTADO");
            }

            // Assign active OrdenTaller if not present (or validate the one sent)
            // Strategy: Always fetch active order to ensure consistency.
            // If the DTO sent an ID, we could validate it matches active, but fetching
            // active is safer for defining "current context".
            com.balsagood.balsagood_app.model.OrdenTaller ordenActiva = ordenTallerRepository
                    .findFirstByOrdenFechaFinIsNull()
                    .orElseThrow(() -> new RuntimeException(
                            "No hay una Orden de Taller activa. Inicie una orden antes de registrar bloques."));

            bloque.setOrdenTaller(ordenActiva);
        }
        return bloqueRepository.save(bloque);
    }

    public Bloque registerPresentado(Bloque bloque) {
        bloque.setEstado("PRESENTADO");
        return bloqueRepository.save(bloque);
    }

    public Bloque updateEncolado(Integer id, BigDecimal pesoConCola) {
        return bloqueRepository.findById(id).map(bloque -> {
            bloque.setBPesoConCola(pesoConCola);
            bloque.setEstado("ENCOLADO");
            return bloqueRepository.save(bloque);
        }).orElseThrow(() -> new RuntimeException("Bloque no encontrado"));
    }

    public List<Bloque> findReadyBlocks() {
        return bloqueRepository.findByEstado("LISTO");
    }

    public void deleteById(Integer id) {
        bloqueRepository.deleteById(id);
    }
}
