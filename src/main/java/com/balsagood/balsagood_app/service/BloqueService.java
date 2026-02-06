package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.dto.dashboard.BloquesPresentadosDTO;
import com.balsagood.balsagood_app.dto.movil.BloqueDTO;
import com.balsagood.balsagood_app.model.Bloque;
import com.balsagood.balsagood_app.model.OrdenTaller;
import com.balsagood.balsagood_app.model.TipoMadera;
import com.balsagood.balsagood_app.repository.BloqueRepository;
import com.balsagood.balsagood_app.repository.OrdenTallerRepository;
import com.balsagood.balsagood_app.repository.TipoMaderaRepository;
import com.balsagood.balsagood_app.util.AppMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<BloqueDTO> findByEstadoPaginated(
            String estado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bloqueRepository.findByEstado(estado, pageable)
                .map(new AppMapper()::toBloqueDTO);
    }

    public Long obtenerUltimoCodigoBloque() {
        return bloqueRepository.obtenerUltimoCodigoBloque();
    }

    public Bloque save(Bloque bloque) {
        // Agregar como "Presentado" un bloque si es primera vez que se registra
        if (bloque.getIdBloque() == null) {
            if (bloque.getEstado() == null) {
                bloque.setEstado("PR");
            }

            // Asignar una orden de taller activa, si no existe, lanzará un mensaje de
            // error.
            OrdenTaller ordenActiva = ordenTallerRepository
                    .findFirstByOrdenFechaFinIsNull()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No hay una Orden de Taller activa. Inicie una orden antes de registrar bloques."));

            bloque.setOrdenTaller(ordenActiva);
        }

        // Validar si existe el tipo de madera de un bloque.
        if (bloque.getTipoMadera() != null && bloque.getTipoMadera().getIdTipoMadera() != null) {
            TipoMadera tipoMadera = tipoMaderaRepository.findById(bloque.getTipoMadera().getIdTipoMadera())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Tipo de madera no encontrado con ID: " + bloque.getTipoMadera().getIdTipoMadera()));
            bloque.setTipoMadera(tipoMadera);
        } else if (bloque.getTipoMadera() == null) {
            // El tipo de madera es obligatorio. Lanzará un error si no se le asignó un
            // tipo.
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
