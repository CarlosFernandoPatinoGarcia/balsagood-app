package com.balsagood.balsagood_app.dto.movil;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CrearLoteDTO {
    private Integer idCamara;
    private LocalDateTime loteFechaInicio;
    private LocalDateTime loteFechaFin;
    private List<Integer> idPallets;
    private String loteObservaciones;
    private String loteCodigo;
}
