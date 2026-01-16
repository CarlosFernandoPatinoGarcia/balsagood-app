package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.RecepcionesMaderaVerdeDTO;
import com.balsagood.balsagood_app.service.ProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/proceso")
public class ProcesoController {

    @Autowired
    private ProcesoService procesoService;

    @GetMapping("/recepciones-madera-verde")
    public ResponseEntity<List<RecepcionesMaderaVerdeDTO>> obtenerReporteRecepciones() {
        List<RecepcionesMaderaVerdeDTO> reporte = procesoService.obtenerReporteRecepciones();
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/pallets-secos")
    public ResponseEntity<List<com.balsagood.balsagood_app.dto.DetallePalletsSecosDTO>> obtenerDetallePalletsSecos() {
        List<com.balsagood.balsagood_app.dto.DetallePalletsSecosDTO> reporte = procesoService
                .obtenerDetallePalletsSecos();
        return ResponseEntity.ok(reporte);
    }
}
