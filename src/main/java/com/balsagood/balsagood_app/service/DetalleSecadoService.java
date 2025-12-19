package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.DetalleSecado;
import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.repository.DetalleSecadoRepository;
import com.balsagood.balsagood_app.repository.PalletVerdeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleSecadoService {

    @Autowired
    private DetalleSecadoRepository detalleSecadoRepository;

    @Autowired
    private PalletVerdeRepository palletVerdeRepository;

    public List<DetalleSecado> findAll() {
        return detalleSecadoRepository.findAll();
    }

    public Optional<DetalleSecado> findById(Integer id) {
        return detalleSecadoRepository.findById(id);
    }

    public DetalleSecado save(DetalleSecado detalleSecado) {
        DetalleSecado saved = detalleSecadoRepository.save(detalleSecado);

        // Update Pallet State to 'SECADORA'
        if (saved.getPalletVerde() != null) {
            PalletVerde pallet = palletVerdeRepository.findById(saved.getPalletVerde().getIdPallet())
                    .orElse(null);
            if (pallet != null) {
                pallet.setPalletEstado("SECADORA");
                palletVerdeRepository.save(pallet);
            }
        }
        return saved;
    }

    public void deleteById(Integer id) {
        detalleSecadoRepository.deleteById(id);
    }
}
