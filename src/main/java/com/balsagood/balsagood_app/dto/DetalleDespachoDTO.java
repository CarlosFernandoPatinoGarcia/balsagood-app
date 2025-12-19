package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleDespachoDTO {
    private Integer idDetalleDespacho;
    private CuerpoDTO cuerpo;
    private DespachoDTO despacho;
}
