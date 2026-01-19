package com.balsagood.balsagood_app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloquesProducidosDTO {
    private Long bloqueCodigo;
    private LocalDateTime ordenFechaInicio;
    private LocalDateTime ordenFechaFin;
    private Double bloqueLargo;
    private Double bloquePesoSinCola;
    private Double bloquePesoConCola;
    private Double bloqueBftFinal;
}
