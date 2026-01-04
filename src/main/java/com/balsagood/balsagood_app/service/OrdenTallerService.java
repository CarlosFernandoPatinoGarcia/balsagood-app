package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.OrdenTaller;
import com.balsagood.balsagood_app.repository.OrdenTallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenTallerService {

    @Autowired
    private OrdenTallerRepository ordenTallerRepository;

    public List<OrdenTaller> findAll() {
        return ordenTallerRepository.findAll();
    }

    public Optional<OrdenTaller> findById(Integer id) {
        return ordenTallerRepository.findById(id);
    }

    public OrdenTaller save(OrdenTaller ordenTaller) {
        return ordenTallerRepository.save(ordenTaller);
    }

    public void deleteById(Integer id) {
        ordenTallerRepository.deleteById(id);
    }

    public Optional<OrdenTaller> obtenerOrdenActiva() {
        return ordenTallerRepository.findFirstByOrdenFechaFinIsNull();
    }

    @Transactional
    public OrdenTaller iniciarOrden() {
        Optional<OrdenTaller> activa = obtenerOrdenActiva();
        if (activa.isPresent()) {
            throw new RuntimeException("Ya existe una orden de taller activa. Finalícela antes de iniciar una nueva.");
        }

        OrdenTaller nuevaOrden = new OrdenTaller();
        nuevaOrden.setOrdenFechaInicio(LocalDateTime.now());
        nuevaOrden.setOrdenFechaFin(null);
        return ordenTallerRepository.save(nuevaOrden);
    }

    @Transactional
    public OrdenTaller finalizarOrden(Integer idOrden) {
        OrdenTaller orden = ordenTallerRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden de taller no encontrada"));

        if (orden.getOrdenFechaFin() != null) {
            throw new RuntimeException("La orden ya fue finalizada anteriormente.");
        }

        orden.setOrdenFechaFin(LocalDateTime.now());
        return ordenTallerRepository.save(orden);
    }
}
