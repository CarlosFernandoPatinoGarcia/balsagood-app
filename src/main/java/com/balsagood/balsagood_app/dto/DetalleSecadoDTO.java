package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleSecadoDTO {
    private Integer idDetalleSecado;
    private PalletVerdeDTO palletVerde;
    private LoteSecadoDTO loteSecado;
    private BigDecimal bftLotePostSecado;
}
