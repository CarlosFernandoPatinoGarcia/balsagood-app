package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.OrdenTaller;
import com.balsagood.balsagood_app.repository.OrdenTallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenTallerService {

    @Autowired
    private OrdenTallerRepository ordenTallerRepository;

    public List<OrdenTaller> findAll() {
        return ordenTallerRepository.findAll();
    }

    public Optional<OrdenTaller> findById(Integer id) {
        return ordenTallerRepository.findById(id);
    }

    public OrdenTaller save(OrdenTaller ordenTaller) {
        return ordenTallerRepository.save(ordenTaller);
    }

    public void deleteById(Integer id) {
        ordenTallerRepository.deleteById(id);
    }
}
