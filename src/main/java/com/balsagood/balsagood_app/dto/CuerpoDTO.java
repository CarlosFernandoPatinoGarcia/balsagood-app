package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuerpoDTO {
    private Integer idCuerpo;
    private BigDecimal cuerpoAnchoFinal;
    private String cuerpoObservacion;
}
