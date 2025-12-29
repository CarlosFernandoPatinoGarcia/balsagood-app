package com.balsagood.balsagood_app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LoteDespachoDTO extends LoteSecadoDTO {
    private List<PalletResumenDTO> pallets;
}
