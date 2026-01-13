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

    @Query("SELECT new com.balsagood.balsagood_app.dto.RegistroInventarioPalletsDTO(" +
            "p.palletEstado, " +
            "tm.tipoDescripcion, " + // 1. Agregamos Tipo Madera
            "CAST(i.espesor AS double), " +
            "COALESCE(SUM(i.bftAceptado), 0)) " + // 2. Evitamos Null
            "FROM ItemPallet i " +
            "JOIN i.palletVerde p " +
            "JOIN p.tipoMadera tm " +
            "WHERE i.estadoItem = 'A' " + // 3. Ignoramos eliminados
            "GROUP BY p.palletEstado, tm.tipoDescripcion, i.espesor")
    List<RegistroInventarioPalletsDTO> obtenerReportePallets();

}
