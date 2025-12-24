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

        BigDecimal palletAncho = new BigDecimal("81").multiply(new BigDecimal(dims.getCantidadPlantilla()));
        pallet.setPalletAncho(palletAncho);

        // New BFT Calculation Logic: Sum of (L * 81 * E * Q) / 12 for each
        // qualification
        // With support for 'Castigo' (Punishment)
        BigDecimal totalBftRecibido = BigDecimal.ZERO;
        BigDecimal totalBftAceptado = BigDecimal.ZERO;

        if (request.getCalificaciones() != null) {
            java.util.List<com.balsagood.balsagood_app.model.ItemPallet> items = new java.util.ArrayList<>();

            for (com.balsagood.balsagood_app.dto.IngresoCompletoRequest.DetalleCalificacion cal : request
                    .getCalificaciones()) {
                com.balsagood.balsagood_app.model.ItemPallet item = new com.balsagood.balsagood_app.model.ItemPallet();
                item.setPalletVerde(pallet);

                BigDecimal largo = cal.getLargo() != null ? cal.getLargo() : BigDecimal.ZERO;
                BigDecimal espesor = cal.getEspesor() != null ? cal.getEspesor() : BigDecimal.ZERO;
                BigDecimal cantidadVal = cal.getCantidad() != null ? new BigDecimal(cal.getCantidad())
                        : BigDecimal.ZERO;
                Boolean esCastigada = cal.getEsCastigada() != null ? cal.getEsCastigada() : false;

                item.setLargo(largo);
                item.setEspesor(espesor);
                item.setCantidad(cantidadVal);
                item.setEsCastigada(esCastigada);
                // Only set original length if it adheres to logic, using default from DTO
                item.setLargoOriginal(cal.getLargoOriginal());

                BigDecimal bftItemRecibido;
                BigDecimal bftItemAceptado;

                if (Boolean.TRUE.equals(esCastigada)) {
                    BigDecimal largoOriginal = cal.getLargoOriginal() != null ? cal.getLargoOriginal() : largo;

                    bftItemRecibido = largoOriginal.multiply(new BigDecimal("81"))
                            .multiply(espesor)
                            .multiply(cantidadVal)
                            .divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);

                    bftItemAceptado = largo.multiply(new BigDecimal("81"))
                            .multiply(espesor)
                            .multiply(cantidadVal)
                            .divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);
                } else {
                    BigDecimal bftItem = largo.multiply(new BigDecimal("81"))
                            .multiply(espesor)
                            .multiply(cantidadVal)
                            .divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);
                    bftItemRecibido = bftItem;
                    bftItemAceptado = bftItem;
                }

                item.setBftRecibido(bftItemRecibido);
                item.setBftAceptado(bftItemAceptado);

                totalBftRecibido = totalBftRecibido.add(bftItemRecibido);
                totalBftAceptado = totalBftAceptado.add(bftItemAceptado);

                items.add(item);
            }
            pallet.setItems(items);
        }

        pallet.setBftVerdeRecibido(totalBftRecibido);
        pallet.setBftVerdeAceptado(totalBftAceptado);

        pallet.setPalletEstado("MADERA VERDE");

        pallet = palletVerdeRepository.save(pallet);

        // Removed CalificacionPallet saving loop as requested.
    }
}
