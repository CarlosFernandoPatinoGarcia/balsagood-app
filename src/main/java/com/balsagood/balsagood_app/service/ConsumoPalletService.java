package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.ConsumoPallet;
import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.repository.ConsumoPalletRepository;
import com.balsagood.balsagood_app.repository.PalletVerdeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumoPalletService {

    @Autowired
    private ConsumoPalletRepository consumoPalletRepository;

    @Autowired
    private PalletVerdeRepository palletVerdeRepository;

    public List<ConsumoPallet> findAll() {
        return consumoPalletRepository.findAll();
    }

    public Optional<ConsumoPallet> findById(Integer id) {
        return consumoPalletRepository.findById(id);
    }

    public ConsumoPallet save(ConsumoPallet consumoPallet) {
        ConsumoPallet saved = consumoPalletRepository.save(consumoPallet);

        // Update Pallet State to 'CONSUMIDO'
        if (saved.getPalletVerde() != null) {
            PalletVerde pallet = palletVerdeRepository.findById(saved.getPalletVerde().getIdPallet())
                    .orElse(null);
            if (pallet != null) {
                pallet.setPalletEstado("CONSUMIDO");
                palletVerdeRepository.save(pallet);
            }
        }
        return saved;
    }

    public void deleteById(Integer id) {
        consumoPalletRepository.deleteById(id);
    }
}
