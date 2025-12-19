package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionPalletDTO {
    private Integer idCalificacion;
    private PalletVerdeDTO palletVerde;
    private LocalDateTime calificacionFecha;
    private BigDecimal calificacionValor;
    private String calificadorUsuario;
    private String calificacionMotivo;
}
