package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.IngresoCompletoRequest;
import com.balsagood.balsagood_app.service.PalletVerdeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingreso")
@CrossOrigin(origins = "*") // Adjust mostly for development
public class IngresoController {

    @Autowired
    private PalletVerdeService palletVerdeService;

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
