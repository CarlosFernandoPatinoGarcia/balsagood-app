package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PalletVerdeDTO {
    private Integer idPallet;
    private RecepcionDTO recepcion;
    private Integer palletNumero;
    private String palletEmplantillador;
    private Integer palletCantPlantillas;
    private BigDecimal palletAncho;
    private BigDecimal palletEspesor;
    private BigDecimal palletLargo;
    private BigDecimal palletAnchoPlantilla;
    private BigDecimal bftVerdeRecibido;
    private BigDecimal bftVerdeAceptado;
    private String palletEstado;
    private String palletObservacion;
}
