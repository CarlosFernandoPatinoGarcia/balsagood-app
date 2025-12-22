package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleSecadoDTO {
    private Integer idDetalleSecado;
    private PalletVerdeDTO palletVerde;
    private LoteSecadoDTO loteSecado;
}
