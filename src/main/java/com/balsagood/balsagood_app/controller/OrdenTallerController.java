package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.OrdenTallerDTO;
import com.balsagood.balsagood_app.model.OrdenTaller;
import com.balsagood.balsagood_app.service.OrdenTallerService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ordenes-taller")
public class OrdenTallerController {

    @Autowired
    private OrdenTallerService ordenTallerService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<OrdenTallerDTO> getAllOrdenesTaller() {
        return ordenTallerService.findAll().stream()
                .map(mapper::toOrdenTallerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenTallerDTO> getOrdenTallerById(@PathVariable Integer id) {
        return ordenTallerService.findById(id)
                .map(mapper::toOrdenTallerDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrdenTallerDTO createOrdenTaller(@RequestBody OrdenTallerDTO dto) {
        OrdenTaller entity = mapper.toOrdenTallerEntity(dto);
        OrdenTaller saved = ordenTallerService.save(entity);
        return mapper.toOrdenTallerDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenTallerDTO> updateOrdenTaller(@PathVariable Integer id,
            @RequestBody OrdenTallerDTO dto) {
        return ordenTallerService.findById(id)
                .map(existing -> {
                    OrdenTaller entity = mapper.toOrdenTallerEntity(dto);
                    entity.setIdOrden(id);
                    OrdenTaller saved = ordenTallerService.save(entity);
                    return ResponseEntity.ok(mapper.toOrdenTallerDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdenTaller(@PathVariable Integer id) {
        if (ordenTallerService.findById(id).isPresent()) {
            ordenTallerService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
