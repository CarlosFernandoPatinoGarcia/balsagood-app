package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.DetalleDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleDespachoRepository extends JpaRepository<DetalleDespacho, Integer> {
}
