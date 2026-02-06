package com.balsagood.balsagood_app.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroInventarioBloquesDTO {
    private String estado; // ("PR" -> PRESENTADO) or ("EN" -> ENCOLADO)
    private String tipoMadera; // ("L" -> Liviana) or ("P" -> Pesada)
    private Double largo; // 25, 24, 23, 22, 21, 20, 18, 16, 14, 12, 10, 8, 6
    private Long cantidad; // Count
    private BigDecimal totalBft; // Sum of BFT
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}