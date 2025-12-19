package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.DespachoDTO;
import com.balsagood.balsagood_app.model.Despacho;
import com.balsagood.balsagood_app.service.DespachoService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/despachos")
public class DespachoController {

    @Autowired
    private DespachoService despachoService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<DespachoDTO> getAllDespachos() {
        return despachoService.findAll().stream()
                .map(mapper::toDespachoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespachoDTO> getDespachoById(@PathVariable Integer id) {
        return despachoService.findById(id)
                .map(mapper::toDespachoDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DespachoDTO createDespacho(@RequestBody DespachoDTO dto) {
        Despacho entity = mapper.toDespachoEntity(dto);
        Despacho saved = despachoService.save(entity);
        return mapper.toDespachoDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespachoDTO> updateDespacho(@PathVariable Integer id, @RequestBody DespachoDTO dto) {
        return despachoService.findById(id)
                .map(existing -> {
                    Despacho entity = mapper.toDespachoEntity(dto);
                    entity.setIdDespacho(id);
                    Despacho saved = despachoService.save(entity);
                    return ResponseEntity.ok(mapper.toDespachoDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDespacho(@PathVariable Integer id) {
        if (despachoService.findById(id).isPresent()) {
            despachoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
