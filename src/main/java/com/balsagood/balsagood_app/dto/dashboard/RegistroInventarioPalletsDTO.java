package com.balsagood.balsagood_app.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroInventarioPalletsDTO {
    private String estado; // "MV", "SE", "SS"
    private String tipoMadera; // "L": "LIVIANA", "P": "PESADA"
    private Double espesor; // 3.0, 2.5, 2, 1.5, 1, 0.875
    private BigDecimal totalBft;
    private LocalDate fechaIngreso;
}