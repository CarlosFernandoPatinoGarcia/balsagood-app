package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.Despacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Integer> {
}
