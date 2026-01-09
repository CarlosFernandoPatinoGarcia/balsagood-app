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
    private TipoMaderaDTO tipoMadera;
    private OrdenTallerDTO ordenTaller;
    private CuerpoDTO cuerpo;
    private BigDecimal bloqueLargo;
    private BigDecimal bloqueAncho;
    private BigDecimal bloqueAlto;
    private BigDecimal bloqueBftFinal;
    private BigDecimal bloquePesoSinCola;
    private BigDecimal bloquePesoConCola;

    private Long bloqueCodigo;

    private String estado;
}
