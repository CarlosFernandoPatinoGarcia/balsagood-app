package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.LoteSecadoDTO;
import com.balsagood.balsagood_app.model.LoteSecado;
import com.balsagood.balsagood_app.service.LoteSecadoService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lotes-secado")
public class LoteSecadoController {

    @Autowired
    private LoteSecadoService loteSecadoService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<LoteSecadoDTO> getAllLotesSecado() {
        return loteSecadoService.findAll().stream()
                .map(mapper::toLoteSecadoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteSecadoDTO> getLoteSecadoById(@PathVariable Integer id) {
        return loteSecadoService.findById(id)
                .map(mapper::toLoteSecadoDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public LoteSecadoDTO createLoteSecado(@RequestBody LoteSecadoDTO dto) {
        LoteSecado entity = mapper.toLoteSecadoEntity(dto);
        LoteSecado saved = loteSecadoService.save(entity);
        return mapper.toLoteSecadoDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoteSecadoDTO> updateLoteSecado(@PathVariable Integer id, @RequestBody LoteSecadoDTO dto) {
        return loteSecadoService.findById(id)
                .map(existing -> {
                    LoteSecado entity = mapper.toLoteSecadoEntity(dto);
                    entity.setIdLote(id);
                    LoteSecado saved = loteSecadoService.save(entity);
                    return ResponseEntity.ok(mapper.toLoteSecadoDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoteSecado(@PathVariable Integer id) {
        if (loteSecadoService.findById(id).isPresent()) {
            loteSecadoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
