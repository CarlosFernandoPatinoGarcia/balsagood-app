package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.LoteSecado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteSecadoRepository extends JpaRepository<LoteSecado, Integer> {
}
