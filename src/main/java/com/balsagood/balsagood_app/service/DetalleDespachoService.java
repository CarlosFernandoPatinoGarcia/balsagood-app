package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.DetalleDespacho;
import com.balsagood.balsagood_app.repository.DetalleDespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleDespachoService {

    @Autowired
    private DetalleDespachoRepository detalleDespachoRepository;

    public List<DetalleDespacho> findAll() {
        return detalleDespachoRepository.findAll();
    }

    public Optional<DetalleDespacho> findById(Integer id) {
        return detalleDespachoRepository.findById(id);
    }

    public DetalleDespacho save(DetalleDespacho detalleDespacho) {
        return detalleDespachoRepository.save(detalleDespacho);
    }

    public void deleteById(Integer id) {
        detalleDespachoRepository.deleteById(id);
    }
}
