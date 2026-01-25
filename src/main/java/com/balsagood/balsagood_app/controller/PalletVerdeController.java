package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.movil.PalletVerdeDTO;
import com.balsagood.balsagood_app.model.PalletVerde;
import com.balsagood.balsagood_app.service.PalletVerdeService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pallets-verdes")
public class PalletVerdeController {

    @Autowired
    private PalletVerdeService palletVerdeService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<PalletVerdeDTO> getAllPalletsVerdes() {
        return palletVerdeService.findAll().stream()
                .map(mapper::toPalletVerdeDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/paginated")
    public org.springframework.http.ResponseEntity<org.springframework.data.domain.Page<PalletVerdeDTO>> getPalletsPaginated(
            @RequestParam(defaultValue = "MV") String estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return org.springframework.http.ResponseEntity.ok(palletVerdeService.findByEstadoPaginated(estado, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PalletVerdeDTO> getPalletVerdeById(@PathVariable Integer id) {
        return palletVerdeService.findById(id)
                .map(mapper::toPalletVerdeDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PalletVerdeDTO createPalletVerde(@RequestBody PalletVerdeDTO dto) {
        PalletVerde entity = mapper.toPalletVerdeEntity(dto);
        PalletVerde saved = palletVerdeService.save(entity);
        return mapper.toPalletVerdeDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PalletVerdeDTO> updatePalletVerde(@PathVariable Integer id,
            @RequestBody PalletVerdeDTO dto) {
        return palletVerdeService.findById(id)
                .map(existing -> {
                    PalletVerde entity = mapper.toPalletVerdeEntity(dto);
                    entity.setIdPallet(id);
                    PalletVerde saved = palletVerdeService.save(entity);
                    return ResponseEntity.ok(mapper.toPalletVerdeDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePalletVerde(@PathVariable Integer id) {
        if (palletVerdeService.findById(id).isPresent()) {
            palletVerdeService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
