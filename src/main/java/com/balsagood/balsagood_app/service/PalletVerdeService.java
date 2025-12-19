package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.repository.PalletVerdeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return palletVerdeRepository.save(palletVerde);
    }

    public void deleteById(Integer id) {
        palletVerdeRepository.deleteById(id);
    }
}
