package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.ConsumoPallet;
import com.balsagood.balsagood_app.repository.ConsumoPalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumoPalletService {

    @Autowired
    private ConsumoPalletRepository consumoPalletRepository;

    public List<ConsumoPallet> findAll() {
        return consumoPalletRepository.findAll();
    }

    public Optional<ConsumoPallet> findById(Integer id) {
        return consumoPalletRepository.findById(id);
    }

    public ConsumoPallet save(ConsumoPallet consumoPallet) {
        return consumoPalletRepository.save(consumoPallet);
    }

    public void deleteById(Integer id) {
        consumoPalletRepository.deleteById(id);
    }
}
