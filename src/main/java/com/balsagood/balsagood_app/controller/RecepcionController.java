package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.RecepcionDTO;
import com.balsagood.balsagood_app.model.Recepcion;
import com.balsagood.balsagood_app.service.RecepcionService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recepciones")
public class RecepcionController {

    @Autowired
    private RecepcionService recepcionService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<RecepcionDTO> getAllRecepciones() {
        return recepcionService.findAll().stream()
                .map(mapper::toRecepcionDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecepcionDTO> getRecepcionById(@PathVariable Integer id) {
        return recepcionService.findById(id)
                .map(mapper::toRecepcionDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public RecepcionDTO createRecepcion(@RequestBody RecepcionDTO recepcionDTO) {
        Recepcion recepcion = mapper.toRecepcionEntity(recepcionDTO);
        Recepcion saved = recepcionService.save(recepcion);
        return mapper.toRecepcionDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecepcionDTO> updateRecepcion(@PathVariable Integer id,
            @RequestBody RecepcionDTO recepcionDTO) {
        return recepcionService.findById(id)
                .map(existingRecepcion -> {
                    Recepcion recepcion = mapper.toRecepcionEntity(recepcionDTO);
                    recepcion.setIdRecepcion(id);
                    Recepcion saved = recepcionService.save(recepcion);
                    return ResponseEntity.ok(mapper.toRecepcionDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecepcion(@PathVariable Integer id) {
        if (recepcionService.findById(id).isPresent()) {
            recepcionService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
