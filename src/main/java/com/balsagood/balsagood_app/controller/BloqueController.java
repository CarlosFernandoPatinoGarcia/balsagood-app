package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.BloqueDTO;
import com.balsagood.balsagood_app.model.Bloque;
import com.balsagood.balsagood_app.service.BloqueService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bloques")
public class BloqueController {

    @Autowired
    private BloqueService bloqueService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<BloqueDTO> getAllBloques() {
        return bloqueService.findAll().stream()
                .map(mapper::toBloqueDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/listos")
    public List<BloqueDTO> getReadyBlocks() {
        return bloqueService.findReadyBlocks().stream()
                .map(mapper::toBloqueDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloqueDTO> getBloqueById(@PathVariable Integer id) {
        return bloqueService.findById(id)
                .map(mapper::toBloqueDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BloqueDTO createBloque(@RequestBody BloqueDTO bloqueDTO) {
        Bloque bloque = mapper.toBloqueEntity(bloqueDTO);
        Bloque saved = bloqueService.save(bloque);
        return mapper.toBloqueDTO(saved);
    }

    @PostMapping("/presentado")
    public BloqueDTO registerPresentado(@RequestBody BloqueDTO bloqueDTO) {
        Bloque bloque = mapper.toBloqueEntity(bloqueDTO);
        Bloque saved = bloqueService.registerPresentado(bloque);
        return mapper.toBloqueDTO(saved);
    }

    @PutMapping("/{id}/encolado")
    public ResponseEntity<BloqueDTO> updateEncolado(@PathVariable Integer id,
            @RequestBody Map<String, BigDecimal> payload) {
        BigDecimal pesoConCola = payload.get("bPesoConCola");
        if (pesoConCola == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Bloque updated = bloqueService.updateEncolado(id, pesoConCola);
            return ResponseEntity.ok(mapper.toBloqueDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloqueDTO> updateBloque(@PathVariable Integer id, @RequestBody BloqueDTO bloqueDTO) {
        return bloqueService.findById(id)
                .map(existingBloque -> {
                    Bloque bloque = mapper.toBloqueEntity(bloqueDTO);
                    bloque.setIdBloque(id);
                    Bloque saved = bloqueService.save(bloque);
                    return ResponseEntity.ok(mapper.toBloqueDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloque(@PathVariable Integer id) {
        if (bloqueService.findById(id).isPresent()) {
            bloqueService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
