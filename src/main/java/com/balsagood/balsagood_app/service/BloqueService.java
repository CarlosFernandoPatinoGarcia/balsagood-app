package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.dto.dashboard.BloquesPresentadosDTO;
import com.balsagood.balsagood_app.model.Bloque;
import com.balsagood.balsagood_app.model.OrdenTaller;
import com.balsagood.balsagood_app.model.TipoMadera;
import com.balsagood.balsagood_app.repository.BloqueRepository;
import com.balsagood.balsagood_app.repository.OrdenTallerRepository;
import com.balsagood.balsagood_app.repository.TipoMaderaRepository;

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
    private OrdenTallerRepository ordenTallerRepository;

    @Autowired
    private TipoMaderaRepository tipoMaderaRepository;

    public List<Bloque> findAll() {
        return bloqueRepository.findAll();
    }

    public Optional<Bloque> findById(Integer id) {
        return bloqueRepository.findById(id);
    }

    public List<BloquesPresentadosDTO> findBloquesPresentados() {
        return bloqueRepository.obtenerBloquesPresentados();
    }

    public Bloque save(Bloque bloque) {
        // Validation for new blocks
        if (bloque.getIdBloque() == null) {
            if (bloque.getEstado() == null) {
                bloque.setEstado("PR");
            }

            // Assign active OrdenTaller if not present (or validate the one sent)
            // Strategy: Always fetch active order to ensure consistency.
            // If the DTO sent an ID, we could validate it matches active, but fetching
            // active is safer for defining "current context".
            OrdenTaller ordenActiva = ordenTallerRepository
                    .findFirstByOrdenFechaFinIsNull()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No hay una Orden de Taller activa. Inicie una orden antes de registrar bloques."));

            bloque.setOrdenTaller(ordenActiva);
        }

        // Validate and assign TipoMadera if present (to ensure it is managed)
        if (bloque.getTipoMadera() != null && bloque.getTipoMadera().getIdTipoMadera() != null) {
            TipoMadera tipoMadera = tipoMaderaRepository.findById(bloque.getTipoMadera().getIdTipoMadera())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Tipo de madera no encontrado con ID: " + bloque.getTipoMadera().getIdTipoMadera()));
            bloque.setTipoMadera(tipoMadera);
        } else if (bloque.getTipoMadera() == null) {
            // Optional: throw exception if TipoMadera is mandatory
            throw new IllegalArgumentException("El tipo de madera es obligatorio para registrar un bloque.");
        }
        return bloqueRepository.save(bloque);
    }

    public Bloque registerPresentado(Bloque bloque) {
        bloque.setEstado("PR");
        return bloqueRepository.save(bloque);
    }

    public Bloque updateEncolado(Integer id, BigDecimal pesoConCola) {
        return bloqueRepository.findById(id).map(bloque -> {
            bloque.setBloquePesoConCola(pesoConCola);
            bloque.setEstado("EN");
            return bloqueRepository.save(bloque);
        }).orElseThrow(() -> new IllegalArgumentException("Bloque no encontrado"));
    }

    public List<Bloque> findReadyBlocks() {
        return bloqueRepository.findByEstado("EN");
    }

    public void deleteById(Integer id) {
        bloqueRepository.deleteById(id);
    }
}
