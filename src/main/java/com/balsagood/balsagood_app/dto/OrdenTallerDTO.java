package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenTallerDTO {
    private Integer idOrden;
    private LocalDateTime ordenFechaInicio;
    private LocalDateTime ordenFechaFin;
    private String ordenObservacion;

    private List<BloqueDTO> bloques;
}
