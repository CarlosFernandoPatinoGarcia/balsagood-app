package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenTallerDTO {
    private Integer idOrden;
    private LocalDate ordenFechaInicio;
    private LocalDate ordenFechaFin;
    private String ordenObservacion;
}
