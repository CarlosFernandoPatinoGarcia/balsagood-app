package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.movil.IngresoCompletoRequest;
import com.balsagood.balsagood_app.service.PalletVerdeService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingreso")

public class IngresoController {

    @Autowired
    private PalletVerdeService palletVerdeService;

    @PostMapping("/ingreso-dashboard")
    public ResponseEntity<?> procesarIngresoCompletoDashboard(@RequestBody List<IngresoCompletoRequest> listaRequests) {
        try {
            palletVerdeService.procesarListaPallets(listaRequests);
            return ResponseEntity.ok().body(Map.of("mensaje", "Pallets procesados correctamente"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }

    }

    @PostMapping("/ingreso-completo")
    public ResponseEntity<String> procesarIngresoCompleto(@RequestBody IngresoCompletoRequest request) {
        try {
            palletVerdeService.procesarIngresoCompleto(request);
            return ResponseEntity.ok("Ingreso procesado correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al procesar ingreso: " + e.getMessage());
        }
    }

}
