package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.CamaraDTO;
import com.balsagood.balsagood_app.model.Camara;
import com.balsagood.balsagood_app.service.CamaraService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/camaras")
public class CamaraController {

    @Autowired
    private CamaraService camaraService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<CamaraDTO> getAllCamaras() {
        return camaraService.findAll().stream()
                .map(mapper::toCamaraDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamaraDTO> getCamaraById(@PathVariable Integer id) {
        return camaraService.findById(id)
                .map(mapper::toCamaraDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CamaraDTO createCamara(@RequestBody CamaraDTO dto) {
        Camara camara = mapper.toCamaraEntity(dto);
        Camara saved = camaraService.save(camara);
        return mapper.toCamaraDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CamaraDTO> updateCamara(@PathVariable Integer id, @RequestBody CamaraDTO dto) {
        return camaraService.findById(id)
                .map(existing -> {
                    Camara camara = mapper.toCamaraEntity(dto);
                    camara.setIdCamara(id);
                    Camara saved = camaraService.save(camara);
                    return ResponseEntity.ok(mapper.toCamaraDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCamara(@PathVariable Integer id) {
        if (camaraService.findById(id).isPresent()) {
            camaraService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
