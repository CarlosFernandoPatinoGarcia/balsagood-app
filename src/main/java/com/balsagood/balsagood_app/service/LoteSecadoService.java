package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.dto.LoteDespachoDTO;
import com.balsagood.balsagood_app.dto.LoteSecadoDTO;
import com.balsagood.balsagood_app.dto.PalletResumenDTO;
import com.balsagood.balsagood_app.model.DetalleSecado;
import com.balsagood.balsagood_app.model.LoteSecado;
import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.repository.DetalleSecadoRepository;
import com.balsagood.balsagood_app.repository.LoteSecadoRepository;
import com.balsagood.balsagood_app.repository.PalletVerdeRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoteSecadoService {

    @Autowired
    private LoteSecadoRepository loteSecadoRepository;

    @Autowired
    private DetalleSecadoRepository detalleSecadoRepository;

    @Autowired
    private PalletVerdeRepository palletVerdeRepository;

    @Autowired
    private com.balsagood.balsagood_app.util.AppMapper appMapper;

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
                    pallet.setPalletEstado("SS");
                    palletVerdeRepository.save(pallet);
                }
            }
        }
        return saved;
    }

    public void deleteById(Integer id) {
        loteSecadoRepository.deleteById(id);
    }

    public LoteDespachoDTO obtenerDatosDespacho(Integer idLote) {
        LoteSecado lote = loteSecadoRepository.findById(idLote)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        List<DetalleSecado> detalles = detalleSecadoRepository.findByLoteSecado_IdLote(idLote);

        // Map base DTO
        LoteSecadoDTO baseDto = appMapper.toLoteSecadoDTO(lote);

        // Create Despacho DTO
        LoteDespachoDTO despachoDTO = new LoteDespachoDTO();
        BeanUtils.copyProperties(baseDto, despachoDTO);

        // Map Pallets
        List<PalletResumenDTO> palletsDTO = detalles.stream()
                .map(d -> {
                    PalletVerde p = d.getPalletVerde();
                    BigDecimal bft = p.getBftVerdeSeco() != null ? p.getBftVerdeSeco() : p.getBftVerdeAceptado();
                    String proveedorName = (p.getRecepcion() != null && p.getRecepcion().getProveedor() != null)
                            ? p.getRecepcion().getProveedor().getProvNombre()
                            : "N/A";
                    String numViaje = (p.getRecepcion() != null) ? String.valueOf(p.getRecepcion().getNumViaje()) : "?";
                    String codigo = "Pallet #" + p.getPalletNumero() + " - Viaje #" + numViaje;
                    String tipoMaderaDescripcion = p.getTipoMadera().getTipoDescripcion();

                    return new PalletResumenDTO(p.getIdPallet(), tipoMaderaDescripcion,
                            codigo, proveedorName,
                            bft);
                })
                .collect(Collectors.toList());

        despachoDTO.setPallets(palletsDTO);
        return despachoDTO;
    }
}
