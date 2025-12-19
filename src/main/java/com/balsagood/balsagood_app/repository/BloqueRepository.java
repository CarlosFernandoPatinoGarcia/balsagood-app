package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.Bloque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueRepository extends JpaRepository<Bloque, Integer> {
}
