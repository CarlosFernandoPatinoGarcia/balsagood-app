package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.dto.RecepcionesMaderaVerdeDTO;
import com.balsagood.balsagood_app.repository.PalletVerdeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcesoService {

    @Autowired
    private PalletVerdeRepository palletVerdeRepository;

    @Autowired
    private com.balsagood.balsagood_app.repository.DetalleSecadoRepository detalleSecadoRepository;

    public List<RecepcionesMaderaVerdeDTO> obtenerReporteRecepciones() {
        return palletVerdeRepository.obtenerReporteRecepciones();
    }

    public List<com.balsagood.balsagood_app.dto.DetallePalletsSecosDTO> obtenerDetallePalletsSecos() {
        return detalleSecadoRepository.obtenerDetallePalletsSecos();
    }
}
