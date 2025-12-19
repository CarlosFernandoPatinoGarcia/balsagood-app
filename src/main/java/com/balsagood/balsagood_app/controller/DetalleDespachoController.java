package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.DetalleDespachoDTO;
import com.balsagood.balsagood_app.model.DetalleDespacho;
import com.balsagood.balsagood_app.service.DetalleDespachoService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detalles-despacho")
public class DetalleDespachoController {

    @Autowired
    private DetalleDespachoService detalleDespachoService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<DetalleDespachoDTO> getAllDetallesDespacho() {
        return detalleDespachoService.findAll().stream()
                .map(mapper::toDetalleDespachoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleDespachoDTO> getDetalleDespachoById(@PathVariable Integer id) {
        return detalleDespachoService.findById(id)
                .map(mapper::toDetalleDespachoDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetalleDespachoDTO createDetalleDespacho(@RequestBody DetalleDespachoDTO dto) {
        DetalleDespacho entity = mapper.toDetalleDespachoEntity(dto);
        DetalleDespacho saved = detalleDespachoService.save(entity);
        return mapper.toDetalleDespachoDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleDespachoDTO> updateDetalleDespacho(@PathVariable Integer id,
            @RequestBody DetalleDespachoDTO dto) {
        return detalleDespachoService.findById(id)
                .map(existing -> {
                    DetalleDespacho entity = mapper.toDetalleDespachoEntity(dto);
                    entity.setIdDetalleDespacho(id);
                    DetalleDespacho saved = detalleDespachoService.save(entity);
                    return ResponseEntity.ok(mapper.toDetalleDespachoDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalleDespacho(@PathVariable Integer id) {
        if (detalleDespachoService.findById(id).isPresent()) {
            detalleDespachoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
