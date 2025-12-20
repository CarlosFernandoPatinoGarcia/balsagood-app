package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.Bloque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BloqueRepository extends JpaRepository<Bloque, Integer> {
    List<Bloque> findByEstado(String estado);
}
