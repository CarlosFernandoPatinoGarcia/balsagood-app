package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.TipoMadera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMaderaRepository extends JpaRepository<TipoMadera, Integer> {
}
