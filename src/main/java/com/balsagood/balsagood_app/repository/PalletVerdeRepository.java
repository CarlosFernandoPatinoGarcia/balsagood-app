package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.PalletVerde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PalletVerdeRepository extends JpaRepository<PalletVerde, Integer> {
    List<PalletVerde> findByPalletEstadoAndBftVerdeAceptadoIsNotNull(String palletEstado);

    List<PalletVerde> findByPalletEstado(String palletEstado);
}
