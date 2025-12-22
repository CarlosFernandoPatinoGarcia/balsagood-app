package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PalletProduccionDTO {
    private Integer idPallet;
    private Integer numViaje;
    private Integer palletNumero;
    private String codigo; // "numViaje-palletNumero"
}
