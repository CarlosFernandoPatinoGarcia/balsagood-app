package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.CrearLoteDTO;
import com.balsagood.balsagood_app.dto.LoteSecadoDTO;
import com.balsagood.balsagood_app.dto.PalletVerdeDTO;
import com.balsagood.balsagood_app.model.LoteSecado;
import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.service.GestionSecadoService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/secado")
@CrossOrigin(origins = "*")
public class GestionSecadoController {

    @Autowired
    private GestionSecadoService gestionSecadoService;

    @Autowired
    private AppMapper appMapper;

    @GetMapping("/disponibles")
    public ResponseEntity<List<PalletVerdeDTO>> getPalletsDisponibles() {
        List<PalletVerde> pallets = gestionSecadoService.getPalletsDisponibles();
        List<PalletVerdeDTO> dtos = pallets.stream()
                .map(appMapper::toPalletVerdeDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/crear")
    public ResponseEntity<LoteSecadoDTO> crearLote(@RequestBody CrearLoteDTO dto) {
        LoteSecado lote = gestionSecadoService.crearLote(dto);
        LoteSecadoDTO resultDTO = appMapper.toLoteSecadoDTO(lote);
        resultDTO.setEstado(gestionSecadoService.getEstadoLote(lote));
        return ResponseEntity.ok(resultDTO);
    }

    @PatchMapping("/finalizar/{idLote}")
    public ResponseEntity<String> finalizarSecado(@PathVariable Integer idLote) {
        gestionSecadoService.finalizarSecado(idLote);
        return ResponseEntity.ok("Secado finalizado correctamente.");
    }
}
