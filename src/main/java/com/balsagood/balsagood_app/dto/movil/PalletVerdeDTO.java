package com.balsagood.balsagood_app.dto.movil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import com.balsagood.balsagood_app.dto.dashboard.RecepcionDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PalletVerdeDTO {
    private Integer idPallet;
    private RecepcionDTO recepcion;
    private TipoMaderaDTO tipoMadera;
    private Integer palletNumero;
    private String palletEmplantillador;
    private BigDecimal palletAnchoPlantilla;
    private BigDecimal bftVerdeRecibido;
    private BigDecimal bftVerdeAceptado;
    private BigDecimal bftVerdeSeco;
    private String palletEstado;
    private String palletObservacion;
}
