package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.PalletProduccionDTO;
import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.service.ProduccionPalletsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produccion-pallets")
@CrossOrigin(origins = "*")
public class ProduccionPalletsController {

    @Autowired
    private ProduccionPalletsService produccionPalletsService;

    @GetMapping("/disponibles")
    public ResponseEntity<List<PalletProduccionDTO>> getPalletsDisponibles() {
        List<PalletVerde> pallets = produccionPalletsService.getPalletsParaProduccion();

        List<PalletProduccionDTO> dtos = pallets.stream().map(pallet -> {
            Integer numViaje = (pallet.getRecepcion() != null) ? pallet.getRecepcion().getNumViaje() : null;
            Integer numPallet = pallet.getPalletNumero();
            String codigo = (numViaje != null && numPallet != null)
                    ? numViaje + "-" + numPallet
                    : "N/A";

            return new PalletProduccionDTO(
                    pallet.getIdPallet(),
                    numViaje,
                    numPallet,
                    codigo);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
