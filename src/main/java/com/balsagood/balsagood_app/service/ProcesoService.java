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

    public List<RecepcionesMaderaVerdeDTO> obtenerReporteRecepciones() {
        return palletVerdeRepository.obtenerReporteRecepciones();
    }
}
