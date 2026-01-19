package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.HistorialPalletsSecando;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialPalletsSecandoRepository extends JpaRepository<HistorialPalletsSecando, Long> {
}
