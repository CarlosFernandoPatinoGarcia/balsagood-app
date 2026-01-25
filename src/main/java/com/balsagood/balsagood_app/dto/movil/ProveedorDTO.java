package com.balsagood.balsagood_app.dto.movil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorDTO {
    private Integer idProveedor;
    private String provNombre;
    private Character provEstado;
}
