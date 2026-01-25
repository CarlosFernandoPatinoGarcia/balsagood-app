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

    @org.springframework.transaction.annotation.Transactional
    public com.balsagood.balsagood_app.dto.movil.RegistroDespachoDTO registrarDespacho(
            com.balsagood.balsagood_app.dto.movil.RegistroDespachoDTO dto) {
        // 1. Guardar Despacho
        Despacho despacho = appMapper.toDespachoEntity(dto.getDespacho());
        despacho = despachoRepository.save(despacho);

        // 2. Crear Cuerpo (Assuming one body for the selected blocks)
        com.balsagood.balsagood_app.model.Cuerpo cuerpo = new com.balsagood.balsagood_app.model.Cuerpo();
        cuerpo.setCuerpoObservacion(dto.getCuerpo().getObservacion());
        // If largoFinal is not provided, maybe calculate it? For now use provided or
        // default.
        cuerpo.setCuerpoBftFinal(
                dto.getCuerpo().getBftFinal() != null ? dto.getCuerpo().getBftFinal() : java.math.BigDecimal.ZERO);
        cuerpo = cuerpoRepository.save(cuerpo);

        // 3. Crear DetalleDespacho
        com.balsagood.balsagood_app.model.DetalleDespacho detalle = new com.balsagood.balsagood_app.model.DetalleDespacho();
        detalle.setDespacho(despacho);
        detalle.setCuerpo(cuerpo);
        detalleDespachoRepository.save(detalle);

        // 4. Actualizar Bloques
        if (dto.getCuerpo().getIdsBloques() != null && !dto.getCuerpo().getIdsBloques().isEmpty()) {
            List<com.balsagood.balsagood_app.model.Bloque> bloques = bloqueRepository
                    .findAllById(dto.getCuerpo().getIdsBloques());
            for (com.balsagood.balsagood_app.model.Bloque bloque : bloques) {
                bloque.setCuerpo(cuerpo);
                bloque.setEstado("EX"); // EXPORTADO
            }
            bloqueRepository.saveAll(bloques);
        }

        // 5. Preparar Response (Full Info)
        com.balsagood.balsagood_app.dto.movil.RegistroDespachoDTO response = new com.balsagood.balsagood_app.dto.movil.RegistroDespachoDTO();
        response.setDespacho(appMapper.toDespachoDTO(despacho));

        com.balsagood.balsagood_app.dto.movil.CuerpoDTO cuerpoResponse = appMapper.toCuerpoDTO(cuerpo);
        // Ensure idsBloques is populated in CuerpoDTO if appMapper doesn't do it
        // automatically (mapper logic dependent)
        // Checking Body Mapper: usually toCuerpoDTO might not fetch children blocks
        // unless explicitly implemented.
        // Assuming appMapper.toCuerpoDTO handles it or we set it manually.
        // Let's set it manually to be safe if mapper is simple.

        // Los idsBloques no se populan en un cuerpoDTO inmediatamente después de
        // crearse, ya que primero
        // se crea el bloque y luego se asocia al cuerpo.
        cuerpoResponse.setIdsBloques(dto.getCuerpo().getIdsBloques());

        response.setCuerpo(cuerpoResponse);

        return response;
    }
}
