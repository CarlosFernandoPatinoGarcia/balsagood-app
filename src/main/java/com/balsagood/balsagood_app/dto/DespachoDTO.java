package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespachoDTO {
    private Integer idDespacho;
    private LocalDate despFecha;
    private String despObservacion;
    private String despCodigo;
}
