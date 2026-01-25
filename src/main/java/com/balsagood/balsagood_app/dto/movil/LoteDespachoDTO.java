package com.balsagood.balsagood_app.dto.movil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

import com.balsagood.balsagood_app.dto.dashboard.PalletResumenDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LoteDespachoDTO extends LoteSecadoDTO {
    private List<PalletResumenDTO> pallets;
}
