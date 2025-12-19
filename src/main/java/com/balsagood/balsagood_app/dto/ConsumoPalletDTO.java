package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumoPalletDTO {
    private Integer idConsumo;
    private OrdenTallerDTO ordenTaller;
    private PalletVerdeDTO palletVerde;
    private LocalDateTime consumoHora;
}
