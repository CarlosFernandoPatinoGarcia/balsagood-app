package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.DetalleDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleDespachoRepository extends JpaRepository<DetalleDespacho, Integer> {

    @org.springframework.data.jpa.repository.Query("SELECT new com.balsagood.balsagood_app.dto.dashboard.BloquesDespachadosDTO("
            +
            "d.despCodigo, " +
            "d.despFecha, " +
            "b.bloqueCodigo, " +
            "CAST(b.bloqueLargo AS double), " +
            "CAST(b.bloqueAncho AS double), " +
            "CAST(b.bloqueAlto AS double), " +
            "CAST(b.bloquePesoConCola AS double)) " +
            "FROM Bloque b, DetalleDespacho dd " +
            "JOIN dd.despacho d " +
            "WHERE b.cuerpo = dd.cuerpo")
    java.util.List<com.balsagood.balsagood_app.dto.dashboard.BloquesDespachadosDTO> obtenerBloquesDespachados();
}
