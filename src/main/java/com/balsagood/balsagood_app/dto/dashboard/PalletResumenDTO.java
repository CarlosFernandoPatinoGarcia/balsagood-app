package com.balsagood.balsagood_app.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PalletResumenDTO {
    private Integer idPallet;
    private String tipoDescripcion;
    private String codigo; // "Pallet #X - Viaje #Y"
    private String proveedor;
    private BigDecimal bft;
}
