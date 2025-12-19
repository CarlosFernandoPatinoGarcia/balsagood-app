package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.CalificacionPallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionPalletRepository extends JpaRepository<CalificacionPallet, Integer> {
}
