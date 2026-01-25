package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.dto.dashboard.BloquesDespachadosDTO;
import com.balsagood.balsagood_app.dto.dashboard.BloquesPresentadosDTO;
import com.balsagood.balsagood_app.dto.dashboard.DetallePalletsSecosDTO;
import com.balsagood.balsagood_app.dto.dashboard.RecepcionesMaderaVerdeDTO;
import com.balsagood.balsagood_app.model.Bloque;
import com.balsagood.balsagood_app.repository.BloqueRepository;
import com.balsagood.balsagood_app.repository.DetalleDespachoRepository;
import com.balsagood.balsagood_app.repository.DetalleSecadoRepository;
import com.balsagood.balsagood_app.repository.PalletVerdeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProcesoService {

    @Autowired
    private PalletVerdeRepository palletVerdeRepository;

    @Autowired
    private DetalleSecadoRepository detalleSecadoRepository;

    @Autowired
    private DetalleDespachoRepository detalleDespachoRepository;

    @Autowired
    private BloqueRepository bloqueRepository;

    @Autowired
    private com.balsagood.balsagood_app.repository.OrdenTallerRepository ordenTallerRepository;

    @Autowired
    private com.balsagood.balsagood_app.repository.TipoMaderaRepository tipoMaderaRepository;

    public List<RecepcionesMaderaVerdeDTO> obtenerReporteRecepciones() {
        return palletVerdeRepository.obtenerReporteRecepciones();
    }

    public List<DetallePalletsSecosDTO> obtenerDetallePalletsSecos() {
        return detalleSecadoRepository.obtenerDetallePalletsSecos();
    }

    public List<BloquesDespachadosDTO> obtenerBloquesDespachados() {
        return detalleDespachoRepository.obtenerBloquesDespachados();
    }

    public List<Bloque> registrarBloquesPresentados(List<Bloque> bloques) {
        // Enforce active OrdenTaller for consistency
        com.balsagood.balsagood_app.model.OrdenTaller ordenActiva = ordenTallerRepository
                .findFirstByOrdenFechaFinIsNull()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No hay una Orden de Taller activa. Inicie una orden antes de registrar bloques."));

        for (Bloque bloque : bloques) {
            bloque.setOrdenTaller(ordenActiva);
            bloque.setEstado("PR");

            // Validate and fetch TipoMadera
            if (bloque.getTipoMadera() != null && bloque.getTipoMadera().getIdTipoMadera() != null) {
                com.balsagood.balsagood_app.model.TipoMadera tipoMadera = tipoMaderaRepository
                        .findById(bloque.getTipoMadera().getIdTipoMadera())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Tipo de madera no encontrado con ID: " + bloque.getTipoMadera().getIdTipoMadera()));
                bloque.setTipoMadera(tipoMadera);
            } else {
                throw new IllegalArgumentException(
                        "DASH-ERROR: El tipo de madera es obligatorio para registrar un bloque.");
            }

            // validar peso con cola y estado, el p. con cola debe ser mayor que el p. sin
            // cola
            if (bloque.getBloquePesoConCola() == null
                    || bloque.getBloquePesoConCola().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "DASH-ERROR: El peso con cola es obligatorio para registrar un bloque.");
            }
            if (bloque.getBloquePesoConCola().compareTo(bloque.getBloquePesoSinCola()) <= 0) {
                throw new IllegalArgumentException("DASH-ERROR: El peso con cola debe ser mayor que el peso sin cola.");
            }
        }
        return bloqueRepository.saveAll(bloques);
    }
}
