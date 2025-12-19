package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.DetalleSecado;
import com.balsagood.balsagood_app.model.LoteSecado;
import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.repository.DetalleSecadoRepository;
import com.balsagood.balsagood_app.repository.LoteSecadoRepository;
import com.balsagood.balsagood_app.repository.PalletVerdeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LoteSecadoService {

    @Autowired
    private LoteSecadoRepository loteSecadoRepository;

    @Autowired
    private DetalleSecadoRepository detalleSecadoRepository;

    @Autowired
    private PalletVerdeRepository palletVerdeRepository;

    public List<LoteSecado> findAll() {
        return loteSecadoRepository.findAll();
    }

    public Optional<LoteSecado> findById(Integer id) {
        return loteSecadoRepository.findById(id);
    }

    public LoteSecado save(LoteSecado loteSecado) {
        LoteSecado saved = loteSecadoRepository.save(loteSecado);

        // If 'fechaFin' is present, update all associated Pallets to 'STOCK SECO'
        if (saved.getLoteFechaFin() != null) {
            List<DetalleSecado> detalles = detalleSecadoRepository.findByLoteSecado(saved);
            for (DetalleSecado detalle : detalles) {
                PalletVerde pallet = detalle.getPalletVerde();
                if (pallet != null) {
                    pallet.setPalletEstado("STOCK SECO");
                    palletVerdeRepository.save(pallet);
                }
            }
        }
        return saved;
    }

    public void deleteById(Integer id) {
        loteSecadoRepository.deleteById(id);
    }
}
