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

    @Query("SELECT new com.balsagood.balsagood_app.dto.RegistroInventarioPalletsDTO(p.palletEstado, CAST(i.espesor AS double), SUM(i.bftAceptado)) "
            +
            "FROM ItemPallet i JOIN i.palletVerde p " +
            "GROUP BY p.palletEstado, i.espesor")
    List<RegistroInventarioPalletsDTO> obtenerReportePallets();

}
