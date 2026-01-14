package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.Camara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CamaraRepository extends JpaRepository<Camara, Integer> {
    java.util.List<Camara> findByCamaraEstado(Character camaraEstado);
}
