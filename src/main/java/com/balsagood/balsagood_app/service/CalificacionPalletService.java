package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.CalificacionPallet;
import com.balsagood.balsagood_app.repository.CalificacionPalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CalificacionPalletService {

    @Autowired
    private CalificacionPalletRepository calificacionPalletRepository;

    public List<CalificacionPallet> findAll() {
        return calificacionPalletRepository.findAll();
    }

    public Optional<CalificacionPallet> findById(Integer id) {
        return calificacionPalletRepository.findById(id);
    }

    public CalificacionPallet save(CalificacionPallet calificacionPallet) {
        return calificacionPalletRepository.save(calificacionPallet);
    }

    public void deleteById(Integer id) {
        calificacionPalletRepository.deleteById(id);
    }
}
