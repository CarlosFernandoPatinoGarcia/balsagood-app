package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.OrdenTaller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenTallerRepository extends JpaRepository<OrdenTaller, Integer> {
}
