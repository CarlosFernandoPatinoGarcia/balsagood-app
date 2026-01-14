package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.Proveedor;
import com.balsagood.balsagood_app.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAll() {
        return proveedorRepository.findByProvEstado('A');
    }

    public Optional<Proveedor> findById(Integer id) {
        return proveedorRepository.findById(id);
    }

    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public void deleteById(Integer id) {
        proveedorRepository.deleteById(id);
    }

    public void softDelete(Integer id) {
        proveedorRepository.findById(id).ifPresent(proveedor -> {
            proveedor.setProvEstado('E');
            proveedorRepository.save(proveedor);
        });
    }

    public List<Proveedor> findByNombreContaining(String nombre) {
        return proveedorRepository.findByProvNombreContainingIgnoreCase(nombre);
    }
}
