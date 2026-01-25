package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.dashboard.BloquesPresentadosDTO;
import com.balsagood.balsagood_app.dto.dashboard.DetallePalletsSecosDTO;
import com.balsagood.balsagood_app.dto.dashboard.RecepcionesMaderaVerdeDTO;
import com.balsagood.balsagood_app.dto.movil.BloqueDTO;
import com.balsagood.balsagood_app.model.Bloque;

import com.balsagood.balsagood_app.service.ProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/proceso")
public class ProcesoController {

    @Autowired
    private ProcesoService procesoService;

    @Autowired
    private com.balsagood.balsagood_app.util.AppMapper appMapper;

    @GetMapping("/recepciones-madera-verde")
    public ResponseEntity<List<RecepcionesMaderaVerdeDTO>> obtenerReporteRecepciones() {
        List<RecepcionesMaderaVerdeDTO> reporte = procesoService.obtenerReporteRecepciones();
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/pallets-secos")
    public ResponseEntity<List<DetallePalletsSecosDTO>> obtenerDetallePalletsSecos() {
        List<DetallePalletsSecosDTO> reporte = procesoService
                .obtenerDetallePalletsSecos();
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/bloques-despachados")
    public ResponseEntity<List<com.balsagood.balsagood_app.dto.dashboard.BloquesDespachadosDTO>> obtenerBloquesDespachados() {
        List<com.balsagood.balsagood_app.dto.dashboard.BloquesDespachadosDTO> reporte = procesoService
                .obtenerBloquesDespachados();
        return ResponseEntity.ok(reporte);
    }

    @PostMapping("/bloques-presentados")
    public ResponseEntity<BloquesPresentadosDTO> registrarBloquesPresentados(
            @RequestBody List<BloqueDTO> bloquesDTO) {

        List<Bloque> bloques = bloquesDTO.stream()
                .map(appMapper::toBloqueEntity)
                .collect(java.util.stream.Collectors.toList());

        List<Bloque> bloquesRegistrados = procesoService.registrarBloquesPresentados(bloques);

        BloquesPresentadosDTO bloquesPresentados = appMapper.toBloquesPresentadosDTO(bloquesRegistrados);
        return ResponseEntity.ok(bloquesPresentados);
    }
}
