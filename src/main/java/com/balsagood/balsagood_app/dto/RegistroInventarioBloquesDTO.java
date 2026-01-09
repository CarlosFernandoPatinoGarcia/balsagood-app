package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroInventarioBloquesDTO {
    private String estado; // "PRESENTADO" or "ENCOLADO"
    private String tipoMadera; // "L" or "P"
    private Double largo; // 25, 24, 23, 22, 21, 20, 18, 16, 14, 12, 10, 8, 6
    private Long cantidad; // Count
    private BigDecimal totalBft; // Sum of BFT
}