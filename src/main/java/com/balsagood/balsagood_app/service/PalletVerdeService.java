package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.dto.movil.IngresoCompletoRequest;
import com.balsagood.balsagood_app.dto.movil.IngresoCompletoRequest.DetalleCalificacion;
import com.balsagood.balsagood_app.model.ItemPallet;
import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.model.Proveedor;
import com.balsagood.balsagood_app.model.TipoMadera;
import com.balsagood.balsagood_app.repository.PalletVerdeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
    private com.balsagood.balsagood_app.repository.ItemPalletRepository itemPalletRepository;

    @Autowired
    private com.balsagood.balsagood_app.repository.TipoMaderaRepository tipoMaderaRepository;

    public List<PalletVerde> findAll() {
        return palletVerdeRepository.findAll();
    }

    public Optional<PalletVerde> findById(Integer id) {
        return palletVerdeRepository.findById(id);
    }

    public PalletVerde save(PalletVerde palletVerde) {
        return palletVerdeRepository.save(palletVerde);
    }

    public void deleteById(Integer id) {
        palletVerdeRepository.deleteById(id);
    }

    @org.springframework.transaction.annotation.Transactional
    public void procesarIngresoCompleto(IngresoCompletoRequest request) {
        // 1. Gestión de Proveedor
        Proveedor proveedor = null;

        if (request.getIdProveedor() != null) {
            proveedor = proveedorRepository.findById(request.getIdProveedor())
                    .orElse(null);
        }

        if (proveedor == null) {
            proveedor = proveedorRepository
                    .findByProvNombre(request.getProvNombre())
                    .orElseGet(() -> {
                        Proveedor newProv = new Proveedor();
                        newProv.setProvNombre(request.getProvNombre());
                        return proveedorRepository.save(newProv);
                    });
        }

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

        // Validate and assign TipoMadera
        if (request.getIdTipoMadera() == null) {
            throw new IllegalArgumentException("El tipo de madera es obligatorio");
        }
        TipoMadera tipoMadera = tipoMaderaRepository.findById(request.getIdTipoMadera())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tipo de madera no encontrado con ID: " + request.getIdTipoMadera()));
        pallet.setTipoMadera(tipoMadera);

        com.balsagood.balsagood_app.dto.movil.IngresoCompletoRequest.Dimensiones dims = request.getDimensiones();
        // Fix Width to 81
        pallet.setPalletAnchoPlantilla(new BigDecimal("81"));

        // New BFT Calculation Logic: Sum of (L * 81 * E * Q) / 12 for each
        // qualification
        // With support for 'Castigo' (Punishment)
        BigDecimal totalBftRecibido = BigDecimal.ZERO;
        BigDecimal totalBftAceptado = BigDecimal.ZERO;

        if (request.getCalificaciones() != null) {
            List<ItemPallet> items = new ArrayList<>();

            for (DetalleCalificacion cal : request
                    .getCalificaciones()) {
                ItemPallet item = new ItemPallet();
                item.setPalletVerde(pallet);

                BigDecimal largo = cal.getLargo() != null ? cal.getLargo() : BigDecimal.ZERO;
                BigDecimal espesor = cal.getEspesor() != null ? cal.getEspesor() : BigDecimal.ZERO;
                BigDecimal cantidadVal = cal.getCantidad() != null ? cal.getCantidad() : BigDecimal.ZERO;
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

                    bftItemRecibido = calculateBft(largoOriginal, espesor, cantidadVal);
                    bftItemAceptado = calculateBft(largo, espesor, cantidadVal);
                } else {
                    BigDecimal bftItem = calculateBft(largo, espesor, cantidadVal);
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

        pallet.setBftVerdeRecibido(round(totalBftRecibido));
        pallet.setBftVerdeAceptado(round(totalBftAceptado));

        pallet.setPalletEstado("MV");

        pallet = palletVerdeRepository.save(pallet);
    }

    private void recalcularTotalesPallet(PalletVerde pallet) {
        if (pallet.getItems() == null || pallet.getItems().isEmpty()) {
            pallet.setBftVerdeRecibido(BigDecimal.ZERO);
            pallet.setBftVerdeAceptado(BigDecimal.ZERO);
            return;
        }

        BigDecimal totalRecibido = BigDecimal.ZERO;
        BigDecimal totalAceptado = BigDecimal.ZERO;

        for (ItemPallet item : pallet.getItems()) {
            // Ignore deleted items
            if ("E".equals(item.getEstadoItem())) {
                continue;
            }

            if (item.getBftRecibido() != null) {
                totalRecibido = totalRecibido.add(item.getBftRecibido());
            }
            if (item.getBftAceptado() != null) {
                totalAceptado = totalAceptado.add(item.getBftAceptado());
            }
        }

        pallet.setBftVerdeRecibido(round(totalRecibido));
        pallet.setBftVerdeAceptado(round(totalAceptado));

        // Guardamos el padre con los nuevos totales
        palletVerdeRepository.save(pallet);
    }

    public void eliminarItem(Integer idItem) {
        ItemPallet item = itemPalletRepository.findById(idItem).orElseThrow();
        PalletVerde pallet = item.getPalletVerde();

        itemPalletRepository.delete(item); // Borras el hijo
        pallet.getItems().remove(item); // Actualizas la lista en memoria (importante para el cálculo)

        recalcularTotalesPallet(pallet); // Recalculas y guardas el padre
    }

    private BigDecimal calculateBft(BigDecimal largo, BigDecimal espesor, BigDecimal cantidad) {
        if (largo == null || espesor == null || cantidad == null) {
            return BigDecimal.ZERO;
        }
        return largo.multiply(new BigDecimal("81"))
                .multiply(espesor)
                .multiply(cantidad)
                .divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);
    }

    private BigDecimal round(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return value.setScale(4, RoundingMode.HALF_UP);
    }

}
