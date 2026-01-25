package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.dashboard.RegistroInventarioBloquesDTO;
import com.balsagood.balsagood_app.dto.dashboard.RegistroInventarioPalletsDTO;
import com.balsagood.balsagood_app.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/pallets")
    public ResponseEntity<List<RegistroInventarioPalletsDTO>> getReportePallets() {
        return ResponseEntity.ok(inventarioService.getReportePallets());
    }

    @GetMapping("/bloques")
    public ResponseEntity<List<RegistroInventarioBloquesDTO>> getReporteBloques() {
        return ResponseEntity.ok(inventarioService.getReporteBloques());
    }
}
