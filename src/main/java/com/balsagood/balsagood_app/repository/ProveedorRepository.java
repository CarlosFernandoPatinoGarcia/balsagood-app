package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.Proveedor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    Optional<Proveedor> findByProvNombre(String provNombre);

    List<Proveedor> findByProvNombreContainingIgnoreCase(String provNombre);

    List<Proveedor> findByProvEstado(Character provEstado);
}
