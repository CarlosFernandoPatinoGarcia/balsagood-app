package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.HistorialPalletsSecandoDTO;
import com.balsagood.balsagood_app.model.HistorialPalletsSecando;
import com.balsagood.balsagood_app.service.HistorialPalletsSecandoService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/historial-pallets-secos")
public class HistorialPalletsSecandoController {

    @Autowired
    private HistorialPalletsSecandoService service;

    @Autowired
    private AppMapper appMapper;

    @GetMapping
    public List<HistorialPalletsSecandoDTO> getAll() {
        return service.findAll().stream()
                .map(appMapper::toHistorialPalletsSecandoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialPalletsSecandoDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(appMapper::toHistorialPalletsSecandoDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HistorialPalletsSecandoDTO create(@RequestBody HistorialPalletsSecandoDTO dto) {
        HistorialPalletsSecando entity = appMapper.toHistorialPalletsSecandoEntity(dto);
        HistorialPalletsSecando saved = service.save(entity);
        return appMapper.toHistorialPalletsSecandoDTO(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
