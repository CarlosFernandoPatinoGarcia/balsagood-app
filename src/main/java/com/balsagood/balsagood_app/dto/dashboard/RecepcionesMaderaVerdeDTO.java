package com.balsagood.balsagood_app.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecepcionesMaderaVerdeDTO {
    private String provNombre;
    private Integer numViaje;
    private Integer palletNumero;
    private String tipoMadera;
    private BigDecimal bftVerdeRecibido;
    private BigDecimal bftVerdeAceptado;
    private LocalDate fechaIngreso;
}
