package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.PalletVerde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PalletVerdeRepository extends JpaRepository<PalletVerde, Integer> {
    List<PalletVerde> findByPalletEstadoAndBftVerdeAceptadoIsNotNull(String palletEstado);

    List<PalletVerde> findByPalletEstado(String palletEstado);

    org.springframework.data.domain.Page<PalletVerde> findByPalletEstado(String palletEstado,
            org.springframework.data.domain.Pageable pageable);

    /*
     * 
     * select prov.prov_nombre, r.num_viaje, p.pallet_numero, tm.tipo_descripcion,
     * p.bft_verde_recibido, p.bft_verde_aceptado, r.fecha_ingreso
     * from proveedor prov
     * inner join recepcion r on prov.id_proveedor = r.id_proveedor
     * inner join pallets_verdes p on p.id_recepcion = r.id_recepcion
     * inner join tipo_madera tm on tm.id_tipo_madera = p.id_tipo_madera
     * where pallet_estado = 'MV'
     * 
     */

    @org.springframework.data.jpa.repository.Query("SELECT new com.balsagood.balsagood_app.dto.dashboard.RecepcionesMaderaVerdeDTO("
            +
            "prov.provNombre, " +
            "r.numViaje, " +
            "p.palletNumero, " +
            "tm.tipoDescripcion, " +
            "p.bftVerdeRecibido, " +
            "p.bftVerdeAceptado, " +
            "r.fechaIngreso) " +
            "FROM PalletVerde p " +
            "JOIN p.recepcion r " +
            "JOIN r.proveedor prov " +
            "JOIN p.tipoMadera tm " +
            "WHERE p.palletEstado = 'MV'")
    List<com.balsagood.balsagood_app.dto.dashboard.RecepcionesMaderaVerdeDTO> obtenerReporteRecepciones();
}
