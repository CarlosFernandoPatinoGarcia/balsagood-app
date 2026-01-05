package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.model.Cuerpo;
import com.balsagood.balsagood_app.model.Bloque;
import com.balsagood.balsagood_app.repository.CuerpoRepository;
import com.balsagood.balsagood_app.repository.BloqueRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CuerpoService {

    @Autowired
    private CuerpoRepository cuerpoRepository;

    @Autowired
    private BloqueRepository bloqueRepository;

    public List<Cuerpo> findAll() {
        return cuerpoRepository.findAll();
    }

    public Optional<Cuerpo> findById(Integer id) {
        return cuerpoRepository.findById(id);
    }

    public Cuerpo save(Cuerpo cuerpo) {
        return cuerpoRepository.save(cuerpo);
    }

    public void deleteById(Integer id) {
        cuerpoRepository.deleteById(id);
    }

    @Transactional // Importante para consistencia
    public Cuerpo agruparBloques(List<Integer> idsBloques, String observacion) {
        // 1. Crear el Cuerpo (Cabecera)
        Cuerpo cuerpo = new Cuerpo();
        cuerpo.setCuerpoObservacion(observacion);
        // Puedes calcular el ancho total aquí sumando los bloques si quieres guardarlo
        cuerpo = cuerpoRepository.save(cuerpo);

        // 2. Buscar y Actualizar los Bloques (Hijos)
        List<Bloque> bloques = bloqueRepository.findAllById(idsBloques);

        for (Bloque bloque : bloques) {
            // Validar que no pertenezca a otro cuerpo
            if (bloque.getCuerpo() != null) {
                throw new RuntimeException("El bloque " + bloque.getBloqueCodigo() + " ya pertenece a un cuerpo.");
            }
            bloque.setCuerpo(cuerpo);
            bloque.setEstado("EXPORTADO"); // O "DESPACHO" según tu flujo
            bloqueRepository.save(bloque);
        }

        return cuerpo;
    }
}
