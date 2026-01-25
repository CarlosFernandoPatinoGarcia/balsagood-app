package com.balsagood.balsagood_app.dto.movil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDespachoDTO {
    private DespachoDTO despacho;
    private CuerpoDTO cuerpo;
}
