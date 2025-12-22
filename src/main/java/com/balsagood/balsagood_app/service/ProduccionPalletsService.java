package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.repository.PalletVerdeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduccionPalletsService {

    @Autowired
    private PalletVerdeRepository palletVerdeRepository;

    public List<PalletVerde> getPalletsParaProduccion() {
        return palletVerdeRepository.findByPalletEstado("STOCK SECO");
    }
}
