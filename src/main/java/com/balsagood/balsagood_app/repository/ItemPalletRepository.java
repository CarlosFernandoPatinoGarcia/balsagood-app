package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.dto.RegistroInventarioPalletsDTO;
import com.balsagood.balsagood_app.model.ItemPallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemPalletRepository extends JpaRepository<ItemPallet, Integer> {
    // agrega la busqueda por id
    Optional<ItemPallet> findById(Integer id);

    /*
     * SELECT
     * p.pallet_estado,
     * tm.tipo_descripcion,
     * CAST(i.espesor AS DOUBLE PRECISION),
     * COALESCE(SUM(i.bft_aceptado), 0) AS total_bft,
     * r.fecha_ingreso
     * FROM items_pallet i
     * JOIN pallets_verdes p ON i.id_pallet = p.id_pallet
     * JOIN tipo_madera tm ON p.id_tipo_madera = tm.id_tipo_madera
     * JOIN recepcion r ON p.id_recepcion = r.id_recepcion
     * WHERE i.estado_item = 'A'
     * GROUP BY
     * p.pallet_estado,
     * tm.tipo_descripcion,
     * i.espesor,
     * r.fecha_ingreso;
     */

    @Query("SELECT new com.balsagood.balsagood_app.dto.RegistroInventarioPalletsDTO(" +
            "p.palletEstado, " +
            "tm.tipoDescripcion, " + // 1. Agregamos Tipo Madera
            "CAST(i.espesor AS double), " +
            "COALESCE(SUM(i.bftAceptado), 0.00), " + // 2. Evitamos Null
            "r.fechaIngreso) " + // 3. Fecha de recepcion para filtros
            "FROM ItemPallet i " +
            "JOIN i.palletVerde p " +
            "JOIN p.tipoMadera tm " +
            "JOIN p.recepcion r " +
            "WHERE i.estadoItem = 'A' " + // 4. Ignoramos eliminados
            "GROUP BY p.palletEstado, tm.tipoDescripcion, i.espesor, r.fechaIngreso")
    List<RegistroInventarioPalletsDTO> obtenerReportePallets();
}
