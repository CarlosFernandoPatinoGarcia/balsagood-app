package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.CuerpoDTO;
import com.balsagood.balsagood_app.model.Cuerpo;
import com.balsagood.balsagood_app.service.CuerpoService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cuerpos")
public class CuerpoController {

    @Autowired
    private CuerpoService cuerpoService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<CuerpoDTO> getAllCuerpos() {
        return cuerpoService.findAll().stream()
                .map(mapper::toCuerpoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuerpoDTO> getCuerpoById(@PathVariable Integer id) {
        return cuerpoService.findById(id)
                .map(mapper::toCuerpoDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CuerpoDTO createCuerpo(@RequestBody CuerpoDTO dto) {
        Cuerpo entity = mapper.toCuerpoEntity(dto);
        Cuerpo saved = cuerpoService.save(entity);
        return mapper.toCuerpoDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuerpoDTO> updateCuerpo(@PathVariable Integer id, @RequestBody CuerpoDTO dto) {
        return cuerpoService.findById(id)
                .map(existing -> {
                    Cuerpo entity = mapper.toCuerpoEntity(dto);
                    entity.setIdCuerpo(id);
                    Cuerpo saved = cuerpoService.save(entity);
                    return ResponseEntity.ok(mapper.toCuerpoDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuerpo(@PathVariable Integer id) {
        if (cuerpoService.findById(id).isPresent()) {
            cuerpoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
