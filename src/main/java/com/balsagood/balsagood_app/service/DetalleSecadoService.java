package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.DetalleSecado;
import com.balsagood.balsagood_app.repository.DetalleSecadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleSecadoService {

    @Autowired
    private DetalleSecadoRepository detalleSecadoRepository;

    public List<DetalleSecado> findAll() {
        return detalleSecadoRepository.findAll();
    }

    public Optional<DetalleSecado> findById(Integer id) {
        return detalleSecadoRepository.findById(id);
    }

    public DetalleSecado save(DetalleSecado detalleSecado) {
        return detalleSecadoRepository.save(detalleSecado);
    }

    public void deleteById(Integer id) {
        detalleSecadoRepository.deleteById(id);
    }
}
