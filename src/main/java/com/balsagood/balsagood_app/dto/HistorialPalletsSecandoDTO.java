package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialPalletsSecandoDTO {
    private Long id;
    private String histLoteCodigo;
    private Integer histNumViaje;
    private Integer histPalletNumero;
    private LocalDate histLoteFechaInicio;
    private LocalDate histLoteFechaFin;
    private String histCamaraDescripcion;
    private Double histBftTotalLote;
    private Double histBftLoteSeco;
}
