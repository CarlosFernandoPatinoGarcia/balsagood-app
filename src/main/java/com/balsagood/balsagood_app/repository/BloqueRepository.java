package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.dto.dashboard.BloquesPresentadosDTO;
import com.balsagood.balsagood_app.dto.dashboard.RegistroInventarioBloquesDTO;
import com.balsagood.balsagood_app.model.Bloque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BloqueRepository extends JpaRepository<Bloque, Integer> {
        List<Bloque> findByEstado(String estado);

        @Query(" SELECT new com.balsagood.balsagood_app.dto.dashboard.BloquesPresentadosDTO("
                        + " b.bloqueCodigo, "
                        + " ot.ordenFechaInicio, "
                        + " ot.ordenFechaFin, "
                        + " CAST(b.bloqueLargo AS double), "
                        + " CAST(b.bloquePesoSinCola AS double), "
                        + " CAST(b.bloquePesoConCola AS double), "
                        + " CAST(b.bloqueBftFinal AS double)) "
                        + " FROM Bloque b "
                        + " JOIN b.ordenTaller ot "
                        + " WHERE b.estado <> 'EX' ")
        List<BloquesPresentadosDTO> obtenerBloquesPresentados();

        // Agregar fechaInicio y fechaFin de la clase OrdenTaller

        /*
         * SELECT b.b_estado, tm.tipo_descripcion, CAST(b.b_largo AS double precision),
         * COUNT(b), SUM(b.b_bft_final), ot.orden_fecha_inicio, ot.orden_fecha_fin
         * FROM bloques b INNER JOIN tipo_madera tm ON tm.id_tipo_madera =
         * b.id_tipo_madera
         * INNER JOIN ordenes_taller ot on ot.id_orden = b.id_orden
         * GROUP BY b.b_estado, tm.tipo_descripcion, b.b_largo, ot.orden_fecha_inicio,
         * ot.orden_fecha_fin
         * 
         */
        @Query("SELECT new com.balsagood.balsagood_app.dto.dashboard.RegistroInventarioBloquesDTO(b.estado, tm.tipoDescripcion, CAST(b.bloqueLargo AS double), COUNT(b), SUM(b.bloqueBftFinal), ot.ordenFechaInicio, ot.ordenFechaFin) "
                        +
                        "FROM Bloque b JOIN b.tipoMadera tm JOIN b.ordenTaller ot " +
                        "GROUP BY b.estado, tm.tipoDescripcion, b.bloqueLargo, ot.ordenFechaInicio, ot.ordenFechaFin")
        List<RegistroInventarioBloquesDTO> obtenerReporteBloques();
}
