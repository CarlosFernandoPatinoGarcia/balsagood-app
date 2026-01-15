package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroInventarioPalletsDTO {
    private String estado; // "DF", "DES", "FIN", "OK", "SE", "SS"
    private String tipoMadera; // "L": "LIVIANA", "P": "PESADA"
    private Double espesor; // 3.0, 2.5, 2, 1.5, 1, 0.875
    private BigDecimal totalBft;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}