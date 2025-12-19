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
}
