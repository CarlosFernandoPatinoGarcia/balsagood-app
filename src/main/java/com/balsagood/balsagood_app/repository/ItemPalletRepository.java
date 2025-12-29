package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.ItemPallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ItemPalletRepository extends JpaRepository<ItemPallet, Integer> {
    // agrega la busqueda por id
    Optional<ItemPallet> findById(Integer id);

}
