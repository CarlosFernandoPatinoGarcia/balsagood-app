package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.repository.PalletVerdeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class PalletVerdeService {

    @Autowired
    private PalletVerdeRepository palletVerdeRepository;

    @Autowired
    private com.balsagood.balsagood_app.repository.ProveedorRepository proveedorRepository;

    @Autowired
    private com.balsagood.balsagood_app.repository.RecepcionRepository recepcionRepository;

    @Autowired
    private com.balsagood.balsagood_app.repository.CalificacionPalletRepository calificacionPalletRepository;

    public List<PalletVerde> findAll() {
        return palletVerdeRepository.findAll();
    }

    public Optional<PalletVerde> findById(Integer id) {
        return palletVerdeRepository.findById(id);
    }

    public PalletVerde save(PalletVerde palletVerde) {
        // Calculate BFT: (Largo * Ancho de Plantilla * Espesor * Numero de plantillas)
        // / 12

        BigDecimal bftRecibido = palletVerde.getPalletLargo()
                .multiply(palletVerde.getPalletAnchoPlantilla())
                .multiply(palletVerde.getPalletEspesor())
                .multiply(new BigDecimal(palletVerde.getPalletCantPlantillas()))
                .divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);

        palletVerde.setBftVerdeRecibido(bftRecibido);

        // Apply 0.9 shrinkage factor
        BigDecimal bftAceptado = bftRecibido.multiply(new BigDecimal("0.9")).setScale(4, RoundingMode.HALF_UP);
        palletVerde.setBftVerdeAceptado(bftAceptado);

        return palletVerdeRepository.save(palletVerde);
    }

    public void deleteById(Integer id) {
        palletVerdeRepository.deleteById(id);
    }

    @org.springframework.transaction.annotation.Transactional
    public void procesarIngresoCompleto(com.balsagood.balsagood_app.dto.IngresoCompletoRequest request) {
        // 1. Gestión de Proveedor
        com.balsagood.balsagood_app.model.Proveedor proveedor = proveedorRepository
                .findByProvNombre(request.getProvNombre())
                .orElseGet(() -> {
                    com.balsagood.balsagood_app.model.Proveedor newProv = new com.balsagood.balsagood_app.model.Proveedor();
                    newProv.setProvNombre(request.getProvNombre());
                    return proveedorRepository.save(newProv);
                });

        // 2. Creación de Recepción
        com.balsagood.balsagood_app.model.Recepcion recepcion = new com.balsagood.balsagood_app.model.Recepcion();
        recepcion.setProveedor(proveedor);
        recepcion.setNumViaje(request.getNumViaje());
        recepcion.setFechaIngreso(
                request.getFechaIngreso() != null ? request.getFechaIngreso() : java.time.LocalDate.now());
        recepcion = recepcionRepository.save(recepcion);

        // 3. Cálculos de Negocio para el Pallet
        PalletVerde pallet = new PalletVerde();
        pallet.setRecepcion(recepcion);
        pallet.setPalletNumero(request.getPalletNumero());
        pallet.setPalletEmplantillador(request.getPalletEmplantillador());

        com.balsagood.balsagood_app.dto.IngresoCompletoRequest.Dimensiones dims = request.getDimensiones();
        pallet.setPalletLargo(dims.getLargo());
        // Fix Width to 81
        pallet.setPalletAnchoPlantilla(new BigDecimal("81"));
        pallet.setPalletEspesor(dims.getEspesor());
        pallet.setPalletCantPlantillas(dims.getCantidadPlantilla());

        // Calculo de Pallet Ancho: ancho_plantilla * cantidad_plantilla
        // Note: keeping existing logic for palletAncho field, but using fixed 81 might
        // be more appropriate?
        // Using dims.getAnchoPlantilla() from request might conflict if we say it is
        // fixed.
        // I will use the fixed value 81 for consistency.
        BigDecimal palletAncho = new BigDecimal("81").multiply(new BigDecimal(dims.getCantidadPlantilla()));
        pallet.setPalletAncho(palletAncho);

        // New BFT Calculation Logic: Sum of (L * 81 * E * Q) / 12 for each
        // qualification
        // With support for 'Castigo' (Punishment)
        BigDecimal totalBftRecibido = BigDecimal.ZERO;
        BigDecimal totalBftAceptado = BigDecimal.ZERO;

        if (request.getCalificaciones() != null) {
            // First pass: Calculate Totals
            for (com.balsagood.balsagood_app.dto.IngresoCompletoRequest.Calificacion cal : request
                    .getCalificaciones()) {
                BigDecimal largo = cal.getLargo() != null ? cal.getLargo() : BigDecimal.ZERO;
                BigDecimal espesor = cal.getEspesor() != null ? cal.getEspesor() : BigDecimal.ZERO;
                BigDecimal cantidad = cal.getCantidad() != null ? new BigDecimal(cal.getCantidad()) : BigDecimal.ZERO;
                Boolean esCastigada = cal.getEsCastigada() != null ? cal.getEsCastigada() : false;

                BigDecimal bftItemRecibido;
                BigDecimal bftItemAceptado;

                if (Boolean.TRUE.equals(esCastigada)) {
                    // Punished Logic
                    BigDecimal largoOriginal = cal.getLargoOriginal() != null ? cal.getLargoOriginal() : largo;

                    // Recibido uses Original Length
                    bftItemRecibido = largoOriginal.multiply(new BigDecimal("81"))
                            .multiply(espesor)
                            .multiply(cantidad)
                            .divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);

                    // Aceptado uses Punished Length (which is the 'largo' field in the item)
                    bftItemAceptado = largo.multiply(new BigDecimal("81"))
                            .multiply(espesor)
                            .multiply(cantidad)
                            .divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);
                } else {
                    // Normal Logic
                    BigDecimal bftItem = largo.multiply(new BigDecimal("81"))
                            .multiply(espesor)
                            .multiply(cantidad)
                            .divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);
                    bftItemRecibido = bftItem;
                    bftItemAceptado = bftItem;
                }

                totalBftRecibido = totalBftRecibido.add(bftItemRecibido);
                totalBftAceptado = totalBftAceptado.add(bftItemAceptado);
            }
        }

        pallet.setBftVerdeRecibido(totalBftRecibido);
        pallet.setBftVerdeAceptado(totalBftAceptado);

        pallet.setPalletEstado("MADERA VERDE");

        pallet = palletVerdeRepository.save(pallet);

        // 5. Registro de Calificaciones
        if (request.getCalificaciones() != null) {
            for (com.balsagood.balsagood_app.dto.IngresoCompletoRequest.Calificacion calRequest : request
                    .getCalificaciones()) {
                com.balsagood.balsagood_app.model.CalificacionPallet cal = new com.balsagood.balsagood_app.model.CalificacionPallet();
                cal.setPalletVerde(pallet);
                cal.setCalificacionValor(calRequest.getValor());
                cal.setCalificacionMotivo(calRequest.getMotivo());
                cal.setCalificacionFecha(java.time.LocalDateTime.now());
                calificacionPalletRepository.save(cal);
            }
        }
    }
}
