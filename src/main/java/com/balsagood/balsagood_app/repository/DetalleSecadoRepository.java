package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.DetalleSecado;
import com.balsagood.balsagood_app.model.LoteSecado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleSecadoRepository extends JpaRepository<DetalleSecado, Integer> {
    List<DetalleSecado> findByLoteSecado(LoteSecado loteSecado);

    List<DetalleSecado> findByLoteSecado_IdLote(Integer idLote);

    @org.springframework.data.jpa.repository.Query("SELECT new com.balsagood.balsagood_app.dto.dashboard.DetallePalletsSecosDTO("
            +
            "ls.loteCodigo, " +
            "CONCAT(r.numViaje, ' ', p.palletNumero), " +
            "ls.loteFechaInicio, " +
            "ls.loteFechaFin, " +
            "c.camaraDescripcion, " +
            "CAST(ls.bftTotalLote AS double), " +
            "CAST(ls.bftLoteSeco AS double)) " +
            "FROM DetalleSecado ds " +
            "JOIN ds.loteSecado ls " +
            "JOIN ds.palletVerde p " +
            "JOIN p.recepcion r " +
            "JOIN ls.camara c " +
            "WHERE p.palletEstado = 'SE'")
    List<com.balsagood.balsagood_app.dto.dashboard.DetallePalletsSecosDTO> obtenerDetallePalletsSecos();
}
