package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloquesDespachadosDTO {

    /*
     * 
     */
    private String codigoDespacho; // Codigo del despacho
    private java.time.LocalDate fechaDespacho; // Fecha de despacho o exportacion
    private Long codigoBloque; // Codigo del bloque
    private Double bloqueLargo; // Largo del bloque
    private Double bloqueAncho; // Ancho del bloque
    private Double bloqueAlto; // Alto del bloque
    private Double bloquePesoConCola; // Peso del bloque con cola
}
