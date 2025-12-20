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
        com.balsagood.balsagood_app.model.Proveedor proveedor = proveedorRepository.findByProvNombre(request.getProvNombre())
            .orElseGet(() -> {
                com.balsagood.balsagood_app.model.Proveedor newProv = new com.balsagood.balsagood_app.model.Proveedor();
                newProv.setProvNombre(request.getProvNombre());
                return proveedorRepository.save(newProv);
            });

        // 2. Creación de Recepción
        com.balsagood.balsagood_app.model.Recepcion recepcion = new com.balsagood.balsagood_app.model.Recepcion();
        recepcion.setProveedor(proveedor);
        recepcion.setNumViaje(request.getNumViaje());
        recepcion.setFechaIngreso(request.getFechaIngreso() != null ? request.getFechaIngreso() : java.time.LocalDate.now());
        recepcion = recepcionRepository.save(recepcion);

        // 3. Cálculos de Negocio para el Pallet
        PalletVerde pallet = new PalletVerde();
        pallet.setRecepcion(recepcion);
        pallet.setPalletNumero(request.getPalletNumero());
        pallet.setPalletEmplantillador(request.getPalletEmplantillador());
        
        com.balsagood.balsagood_app.dto.IngresoCompletoRequest.Dimensiones dims = request.getDimensiones();
        pallet.setPalletLargo(dims.getLargo());
        pallet.setPalletAnchoPlantilla(dims.getAnchoPlantilla());
        pallet.setPalletEspesor(dims.getEspesor());
        pallet.setPalletCantPlantillas(dims.getCantidadPlantilla());
        
        // Calculo de Pallet Ancho: ancho_plantilla * cantidad_plantilla
        BigDecimal palletAncho = dims.getAnchoPlantilla().multiply(new BigDecimal(dims.getCantidadPlantilla()));
        pallet.setPalletAncho(palletAncho);

        // Se usa save() interno para reutilizar lógica de BFT si se desea, pero aqui reimplementamos para asegurar flujo
        // Ojo: save() ya tiene logica BFT. Podemos setear valores y llamar repository.save, pero faltaria logica.
        // Llamaremos a nuestro propio calculateBft logic aqui o usamos el save simple y dejamos que el metodo save lo haga?
        // El metodo save() original hace el calculo. Asi que solo seteamos propiedades y guardamos.
        // Pero save() espera un objeto ya con otros campos.
        // Reutilizaremos la logica.
        
        BigDecimal bftRecibido = dims.getLargo()
                .multiply(dims.getAnchoPlantilla())
                .multiply(dims.getEspesor())
                .multiply(new BigDecimal(dims.getCantidadPlantilla()))
                .divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);
        pallet.setBftVerdeRecibido(bftRecibido);

        BigDecimal bftAceptado = bftRecibido.multiply(new BigDecimal("0.9")).setScale(4, RoundingMode.HALF_UP);
        pallet.setBftVerdeAceptado(bftAceptado);
        
        pallet.setPalletEstado("MADERA VERDE"); // Por defecto o por Requisito 4
        
        pallet = palletVerdeRepository.save(pallet);

        // 5. Registro de Calificaciones
        if (request.getCalificaciones() != null) {
            for (com.balsagood.balsagood_app.dto.IngresoCompletoRequest.Calificacion calRequest : request.getCalificaciones()) {
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
