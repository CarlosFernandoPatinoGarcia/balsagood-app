package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloqueDTO {
    private Integer idBloque;
    private OrdenTallerDTO ordenTaller;
    private CuerpoDTO cuerpo;
    private BigDecimal bLargo;
    private BigDecimal bAncho;
    private BigDecimal bAlto;
    private BigDecimal bBftFinal;
    private BigDecimal bPesoSinCola;
    private BigDecimal bPesoConCola;
    private Long bCodigo;
    private String estado;
}
