package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.ConsumoPallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumoPalletRepository extends JpaRepository<ConsumoPallet, Integer> {
}
