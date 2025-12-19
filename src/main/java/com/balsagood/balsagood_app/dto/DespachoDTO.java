package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespachoDTO {
    private Integer idDespacho;
    private LocalDateTime despFecha;
    private String despObservacion;
}
