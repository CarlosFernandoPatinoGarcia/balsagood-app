package com.balsagood.balsagood_app.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import com.balsagood.balsagood_app.dto.movil.ProveedorDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecepcionDTO {
    private Integer idRecepcion;
    private ProveedorDTO proveedor;
    private Integer numViaje;
    private LocalDate fechaIngreso;
}
