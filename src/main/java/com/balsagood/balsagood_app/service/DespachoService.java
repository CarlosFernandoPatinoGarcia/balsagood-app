package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.dto.movil.CuerpoDTO;
import com.balsagood.balsagood_app.dto.movil.RegistroDespachoDTO;
import com.balsagood.balsagood_app.model.Bloque;
import com.balsagood.balsagood_app.model.Cuerpo;
import com.balsagood.balsagood_app.model.Despacho;
import com.balsagood.balsagood_app.model.DetalleDespacho;
import com.balsagood.balsagood_app.repository.DespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DespachoService {

    @Autowired
    private com.balsagood.balsagood_app.repository.CuerpoRepository cuerpoRepository;
    @Autowired
    private com.balsagood.balsagood_app.repository.DetalleDespachoRepository detalleDespachoRepository;
    @Autowired
    private com.balsagood.balsagood_app.repository.BloqueRepository bloqueRepository;
    @Autowired
    private com.balsagood.balsagood_app.util.AppMapper appMapper;

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

    @Transactional
    public RegistroDespachoDTO registrarDespacho(
            RegistroDespachoDTO dto) {
        // 1. Guardar Despacho
        Despacho despacho = appMapper.toDespachoEntity(dto.getDespacho());
        despacho = despachoRepository.save(despacho);

        // 2. Crear Cuerpo
        Cuerpo cuerpo = new Cuerpo();
        cuerpo.setCuerpoObservacion(dto.getCuerpo().getObservacion());
        cuerpo.setCuerpoBftFinal(
                dto.getCuerpo().getBftFinal() != null ? dto.getCuerpo().getBftFinal() : java.math.BigDecimal.ZERO);
        cuerpo = cuerpoRepository.save(cuerpo);

        // 3. Crear DetalleDespacho
        DetalleDespacho detalle = new DetalleDespacho();
        detalle.setDespacho(despacho);
        detalle.setCuerpo(cuerpo);
        detalleDespachoRepository.save(detalle);

        // 4. Actualizar Bloques
        if (dto.getCuerpo().getIdsBloques() != null && !dto.getCuerpo().getIdsBloques().isEmpty()) {
            List<Bloque> bloques = bloqueRepository
                    .findAllById(dto.getCuerpo().getIdsBloques());
            for (Bloque bloque : bloques) {
                bloque.setCuerpo(cuerpo);
                bloque.setEstado("EX"); // EXPORTADO
            }
            bloqueRepository.saveAll(bloques);
        }

        // 5. Preparar Response (Full Info)
        RegistroDespachoDTO response = new RegistroDespachoDTO();
        response.setDespacho(appMapper.toDespachoDTO(despacho));

        CuerpoDTO cuerpoResponse = appMapper.toCuerpoDTO(cuerpo);

        cuerpoResponse.setIdsBloques(dto.getCuerpo().getIdsBloques());

        response.setCuerpo(cuerpoResponse);

        return response;
    }
}
