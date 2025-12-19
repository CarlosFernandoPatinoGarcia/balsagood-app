package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.DetalleSecado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleSecadoRepository extends JpaRepository<DetalleSecado, Integer> {
}
