package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.DetalleSecadoDTO;
import com.balsagood.balsagood_app.model.DetalleSecado;
import com.balsagood.balsagood_app.service.DetalleSecadoService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detalles-secado")
public class DetalleSecadoController {

    @Autowired
    private DetalleSecadoService detalleSecadoService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<DetalleSecadoDTO> getAllDetallesSecado() {
        return detalleSecadoService.findAll().stream()
                .map(mapper::toDetalleSecadoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleSecadoDTO> getDetalleSecadoById(@PathVariable Integer id) {
        return detalleSecadoService.findById(id)
                .map(mapper::toDetalleSecadoDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetalleSecadoDTO createDetalleSecado(@RequestBody DetalleSecadoDTO dto) {
        DetalleSecado entity = mapper.toDetalleSecadoEntity(dto);
        DetalleSecado saved = detalleSecadoService.save(entity);
        return mapper.toDetalleSecadoDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleSecadoDTO> updateDetalleSecado(@PathVariable Integer id,
            @RequestBody DetalleSecadoDTO dto) {
        return detalleSecadoService.findById(id)
                .map(existing -> {
                    DetalleSecado entity = mapper.toDetalleSecadoEntity(dto);
                    entity.setIdDetalleSecado(id);
                    DetalleSecado saved = detalleSecadoService.save(entity);
                    return ResponseEntity.ok(mapper.toDetalleSecadoDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalleSecado(@PathVariable Integer id) {
        if (detalleSecadoService.findById(id).isPresent()) {
            detalleSecadoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
