package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.LoteSecado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface LoteSecadoRepository extends JpaRepository<LoteSecado, Integer> {
    @Query("SELECT DISTINCT l.camara.idCamara FROM LoteSecado l WHERE l.bftTotalLote IS NULL")
    List<Integer> findOccupiedCamaraIds();
}
