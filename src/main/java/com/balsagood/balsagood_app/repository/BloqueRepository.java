package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.dto.RegistroInventarioBloquesDTO;
import com.balsagood.balsagood_app.model.Bloque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BloqueRepository extends JpaRepository<Bloque, Integer> {
    List<Bloque> findByEstado(String estado);

    @Query("SELECT new com.balsagood.balsagood_app.dto.RegistroInventarioBloquesDTO(b.estado, tm.tipoDescripcion, CAST(b.bloqueLargo AS double), COUNT(b), SUM(b.bloqueBftFinal)) "
            +
            "FROM Bloque b JOIN b.tipoMadera tm " +
            "GROUP BY b.estado, tm.tipoDescripcion, b.bloqueLargo")
    List<RegistroInventarioBloquesDTO> obtenerReporteBloques();
}
