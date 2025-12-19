package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.CalificacionPalletDTO;
import com.balsagood.balsagood_app.model.CalificacionPallet;
import com.balsagood.balsagood_app.service.CalificacionPalletService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calificaciones-pallets")
public class CalificacionPalletController {

    @Autowired
    private CalificacionPalletService calificacionPalletService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<CalificacionPalletDTO> getAllCalificacionesPallets() {
        return calificacionPalletService.findAll().stream()
                .map(mapper::toCalificacionPalletDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalificacionPalletDTO> getCalificacionPalletById(@PathVariable Integer id) {
        return calificacionPalletService.findById(id)
                .map(mapper::toCalificacionPalletDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CalificacionPalletDTO createCalificacionPallet(@RequestBody CalificacionPalletDTO dto) {
        CalificacionPallet entity = mapper.toCalificacionPalletEntity(dto);
        CalificacionPallet saved = calificacionPalletService.save(entity);
        return mapper.toCalificacionPalletDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalificacionPalletDTO> updateCalificacionPallet(@PathVariable Integer id,
            @RequestBody CalificacionPalletDTO dto) {
        return calificacionPalletService.findById(id)
                .map(existing -> {
                    CalificacionPallet entity = mapper.toCalificacionPalletEntity(dto);
                    entity.setIdCalificacion(id);
                    CalificacionPallet saved = calificacionPalletService.save(entity);
                    return ResponseEntity.ok(mapper.toCalificacionPalletDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalificacionPallet(@PathVariable Integer id) {
        if (calificacionPalletService.findById(id).isPresent()) {
            calificacionPalletService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
