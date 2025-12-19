package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.ConsumoPalletDTO;
import com.balsagood.balsagood_app.model.ConsumoPallet;
import com.balsagood.balsagood_app.service.ConsumoPalletService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consumos-pallets")
public class ConsumoPalletController {

    @Autowired
    private ConsumoPalletService consumoPalletService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<ConsumoPalletDTO> getAllConsumosPallets() {
        return consumoPalletService.findAll().stream()
                .map(mapper::toConsumoPalletDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumoPalletDTO> getConsumoPalletById(@PathVariable Integer id) {
        return consumoPalletService.findById(id)
                .map(mapper::toConsumoPalletDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ConsumoPalletDTO createConsumoPallet(@RequestBody ConsumoPalletDTO dto) {
        ConsumoPallet entity = mapper.toConsumoPalletEntity(dto);
        ConsumoPallet saved = consumoPalletService.save(entity);
        return mapper.toConsumoPalletDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumoPalletDTO> updateConsumoPallet(@PathVariable Integer id,
            @RequestBody ConsumoPalletDTO dto) {
        return consumoPalletService.findById(id)
                .map(existing -> {
                    ConsumoPallet entity = mapper.toConsumoPalletEntity(dto);
                    entity.setIdConsumo(id);
                    ConsumoPallet saved = consumoPalletService.save(entity);
                    return ResponseEntity.ok(mapper.toConsumoPalletDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumoPallet(@PathVariable Integer id) {
        if (consumoPalletService.findById(id).isPresent()) {
            consumoPalletService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
