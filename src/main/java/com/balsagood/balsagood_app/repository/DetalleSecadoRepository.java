package com.balsagood.balsagood_app.repository;

import com.balsagood.balsagood_app.model.DetalleSecado;
import com.balsagood.balsagood_app.model.LoteSecado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleSecadoRepository extends JpaRepository<DetalleSecado, Integer> {
    List<DetalleSecado> findByLoteSecado(LoteSecado loteSecado);
}
