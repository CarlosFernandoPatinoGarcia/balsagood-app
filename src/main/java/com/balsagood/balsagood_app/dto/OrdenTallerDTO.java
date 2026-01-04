package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrdenTallerDTO {
    private Integer idOrden;
    private LocalDateTime ordenFechaInicio;
    private LocalDateTime ordenFechaFin;
    private String ordenObservacion;

    public OrdenTallerDTO(Integer idOrden, LocalDateTime ordenFechaInicio, LocalDateTime ordenFechaFin,
            String ordenObservacion) {
        this.idOrden = idOrden;
        this.ordenFechaInicio = ordenFechaInicio;
        this.ordenFechaFin = ordenFechaFin;
        this.ordenObservacion = ordenObservacion;
    }
}
