package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.dto.movil.IngresoCompletoRequest;
import com.balsagood.balsagood_app.dto.movil.IngresoCompletoRequest.DetalleCalificacion;
import com.balsagood.balsagood_app.dto.movil.PalletVerdeDTO;
import com.balsagood.balsagood_app.model.ItemPallet;
import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.model.Proveedor;
import com.balsagood.balsagood_app.model.Recepcion;
import com.balsagood.balsagood_app.model.TipoMadera;
import com.balsagood.balsagood_app.repository.ItemPalletRepository;
import com.balsagood.balsagood_app.repository.PalletVerdeRepository;
import com.balsagood.balsagood_app.repository.ProveedorRepository;
import com.balsagood.balsagood_app.repository.RecepcionRepository;
import com.balsagood.balsagood_app.repository.TipoMaderaRepository;
import com.balsagood.balsagood_app.util.AppMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ProveedorRepository proveedorRepository;

    @Autowired
    private RecepcionRepository recepcionRepository;

    @Autowired
    private ItemPalletRepository itemPalletRepository;

    @Autowired
    private TipoMaderaRepository tipoMaderaRepository;

    public List<PalletVerde> findAll() {
        return palletVerdeRepository.findAll();
    }

    @Autowired
    private AppMapper mapper;

    public Page<PalletVerdeDTO> findByEstadoPaginated(
            String estado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return palletVerdeRepository.findByPalletEstado(estado, pageable)
                .map(mapper::toPalletVerdeDTO);
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

    @Transactional
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
        Recepcion recepcion = new Recepcion();
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

        // Fix Width to 81
        pallet.setPalletAnchoPlantilla(new BigDecimal("81"));

        // Formula para Cubicar Pallets: BFT = (Largo * (81 -> Ancho de plantilla) *
        // Espesor *
        // Cantidad de plantillas) / 12
        // Se aplica castigo

        BigDecimal totalBftRecibido = BigDecimal.ZERO;
        BigDecimal totalBftAceptado = BigDecimal.ZERO;

        if (request.getCalificaciones() != null) {
            List<ItemPallet> items = new ArrayList<>();

            // Se itera por todas las calificaciones de cada pallet para sumarlas y obtener
            // el BFT Total con castigo incluido
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
                // Seteamos el largo original por si existe más de una calificación en el
                // pallet.
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

    @Transactional
    /**
     * Esta función recibe una lista que el frontend Web envía. Se reutiliza la
     * lógica donde se registraban pallets uno por uno.
     * 
     * @param listaRequests
     */
    public void procesarListaPallets(List<IngresoCompletoRequest> listaRequests) {
        if (listaRequests == null || listaRequests.isEmpty()) {
            throw new IllegalArgumentException("La lista de pallets no puede estar vacía.");
        }

        for (IngresoCompletoRequest request : listaRequests) {
            procesarIngresoCompleto(request);
        }
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

        itemPalletRepository.delete(item);
        pallet.getItems().remove(item);

        recalcularTotalesPallet(pallet);
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
