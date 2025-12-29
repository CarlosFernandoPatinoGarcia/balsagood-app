package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.dto.CrearLoteDTO;
import com.balsagood.balsagood_app.model.*;
import com.balsagood.balsagood_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GestionSecadoService {

    @Autowired
    private PalletVerdeRepository palletVerdeRepository;

    @Autowired
    private LoteSecadoRepository loteSecadoRepository;

    @Autowired
    private DetalleSecadoRepository detalleSecadoRepository;

    @Autowired
    private CamaraRepository camaraRepository;

    // 1. Filtrado de Pallets Disponibles
    public List<PalletVerde> getPalletsDisponibles() {
        return palletVerdeRepository.findByPalletEstadoAndBftVerdeAceptadoIsNotNull("MADERA VERDE");
    }

    // 2. Creación de Lotes
    @Transactional
    public LoteSecado crearLote(CrearLoteDTO dto) {
        // Validar Cámara
        Camara camara = camaraRepository.findById(dto.getIdCamara())
                .orElseThrow(() -> new RuntimeException("Cámara no encontrada"));

        // Validar Pallets y Capacidad
        List<PalletVerde> palletsSeleccionados = palletVerdeRepository.findAllById(dto.getIdPallets());
        if (palletsSeleccionados.size() != dto.getIdPallets().size()) {
            throw new RuntimeException("Algunos pallets no fueron encontrados");
        }

        BigDecimal volumenTotal = BigDecimal.ZERO;
        for (PalletVerde pallet : palletsSeleccionados) {
            if (!"MADERA VERDE".equals(pallet.getPalletEstado())) {
                throw new RuntimeException("El pallet " + pallet.getPalletNumero() + " no está en estado MADERA VERDE");
            }
            volumenTotal = volumenTotal.add(pallet.getBftVerdeAceptado());
        }

        // Validar Capacidad Disponible (usando el campo persistido)
        if (volumenTotal.compareTo(camara.getCapacidadDisponible()) > 0) {
            throw new RuntimeException("La capacidad de la cámara es insuficiente. Disponible: " +
                    camara.getCapacidadDisponible() + ", Requerido: " + volumenTotal);
        }

        // Actualizar capacidad disponible de la cámara
        camara.setCapacidadDisponible(camara.getCapacidadDisponible().subtract(volumenTotal));
        camaraRepository.save(camara);

        // Crear Lote
        LoteSecado lote = new LoteSecado();
        lote.setCamara(camara);
        lote.setLoteCodigo(dto.getLoteCodigo());
        lote.setLoteFechaInicio(dto.getLoteFechaInicio());
        lote.setLoteFechaFin(dto.getLoteFechaFin());
        lote.setLoteObservaciones(dto.getLoteObservaciones());
        // Guardar el BFT Total (Verde/Entrada) en el lote
        lote.setBftTotalLote(volumenTotal);
        // El BFT Seco se calculará al finalizar
        lote.setBftLoteSeco(null);
        lote = loteSecadoRepository.save(lote);

        // Crear Detalle y Actualizar Pallets
        for (PalletVerde pallet : palletsSeleccionados) {
            DetalleSecado detalle = new DetalleSecado();
            detalle.setLoteSecado(lote);
            detalle.setPalletVerde(pallet);
            detalleSecadoRepository.save(detalle);

            pallet.setPalletEstado("EN SECADO");
            palletVerdeRepository.save(pallet);
        }

        return lote;
    }

    // 3. Lógica de Estados del Lote
    public String getEstadoLote(LoteSecado lote) {
        LocalDateTime now = LocalDateTime.now();
        if (lote.getLoteFechaFin() != null && now.isAfter(lote.getLoteFechaFin())) {
            return "LISTO PARA BFT";
        } else if (now.isAfter(lote.getLoteFechaInicio())
                && (lote.getLoteFechaFin() == null || now.isBefore(lote.getLoteFechaFin()))) {
            return "SECANDO";
        }
        return "PROGRAMADO"; // Estado por defecto si no ha iniciado
    }

    // 4. Finalización de Secado
    @Transactional
    public void finalizarSecado(Integer idLote) {
        LoteSecado lote = loteSecadoRepository.findById(idLote)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        if (lote.getBftLoteSeco() != null) {
            throw new RuntimeException("El lote ya ha sido finalizado previamente.");
        }

        List<DetalleSecado> detalles = detalleSecadoRepository.findByLoteSecado_IdLote(idLote);
        BigDecimal sumatoriaBftSeco = BigDecimal.ZERO;
        BigDecimal factorMerma = new BigDecimal("0.98");

        for (DetalleSecado detalle : detalles) {
            PalletVerde pallet = detalle.getPalletVerde();

            // Calcular BFT Seco del Pallet
            if (pallet.getBftVerdeAceptado() != null) {
                BigDecimal bftPalletSeco = pallet.getBftVerdeAceptado().multiply(factorMerma).setScale(2,
                        RoundingMode.HALF_UP);
                pallet.setBftVerdeSeco(bftPalletSeco);
                sumatoriaBftSeco = sumatoriaBftSeco.add(bftPalletSeco);
            }

            // Cambiar estado a STOCK SECO
            pallet.setPalletEstado("STOCK SECO");
            palletVerdeRepository.save(pallet);
        }

        // Registrar el BFT Final (Seco) en el lote
        lote.setBftLoteSeco(sumatoriaBftSeco);
        loteSecadoRepository.save(lote);

        // Actualizar capacidad disponible de la cámara (liberar espacio)
        // El espacio liberado es el BFT VERDE DE ENTRADA (bftTotalLote)
        Camara camara = lote.getCamara();
        if (lote.getBftTotalLote() != null) {
            camara.setCapacidadDisponible(camara.getCapacidadDisponible().add(lote.getBftTotalLote()));
            // Asegurar que no exceda la capacidad total (por si acaso hay inconsistencias
            // manuales)
            if (camara.getCapacidadDisponible().compareTo(camara.getCamaraCapacidad()) > 0) {
                camara.setCapacidadDisponible(camara.getCamaraCapacidad());
            }
            camaraRepository.save(camara);
        }
    }
}
