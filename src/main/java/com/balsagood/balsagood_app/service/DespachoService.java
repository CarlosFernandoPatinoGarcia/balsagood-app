package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.Despacho;
import com.balsagood.balsagood_app.repository.DespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DespachoService {

    @Autowired
    private DespachoRepository despachoRepository;

    public List<Despacho> findAll() {
        return despachoRepository.findAll();
    }

    public Optional<Despacho> findById(Integer id) {
        return despachoRepository.findById(id);
    }

    public Despacho save(Despacho despacho) {
        return despachoRepository.save(despacho);
    }

    public void deleteById(Integer id) {
        despachoRepository.deleteById(id);
    }
}
