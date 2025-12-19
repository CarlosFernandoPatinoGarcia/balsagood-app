package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteSecadoDTO {
    private Integer idLote;
    private CamaraDTO camara;
    private LocalDateTime loteFechaInicio;
    private LocalDateTime loteFechaFin;
    private String loteObservaciones;
}
