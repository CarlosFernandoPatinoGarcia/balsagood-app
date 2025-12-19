package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.ProveedorDTO;
import com.balsagood.balsagood_app.model.Proveedor;
import com.balsagood.balsagood_app.service.ProveedorService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private AppMapper mapper;

    @GetMapping
    public List<ProveedorDTO> getAllProveedores() {
        return proveedorService.findAll().stream()
                .map(mapper::toProveedorDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable Integer id) {
        return proveedorService.findById(id)
                .map(mapper::toProveedorDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProveedorDTO createProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        Proveedor proveedor = mapper.toProveedorEntity(proveedorDTO);
        Proveedor saved = proveedorService.save(proveedor);
        return mapper.toProveedorDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> updateProveedor(@PathVariable Integer id,
            @RequestBody ProveedorDTO proveedorDTO) {
        return proveedorService.findById(id)
                .map(existingProveedor -> {
                    Proveedor proveedor = mapper.toProveedorEntity(proveedorDTO);
                    proveedor.setIdProveedor(id);
                    Proveedor saved = proveedorService.save(proveedor);
                    return ResponseEntity.ok(mapper.toProveedorDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Integer id) {
        if (proveedorService.findById(id).isPresent()) {
            proveedorService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
