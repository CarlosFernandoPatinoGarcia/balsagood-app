package com.balsagood.balsagood_app.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamaraDTO {
    private Integer idCamara;
    private String camaraDescripcion;
    private BigDecimal camaraCapacidad;
    private BigDecimal capacidadDisponible;
    private Character camaraEstado;
}
