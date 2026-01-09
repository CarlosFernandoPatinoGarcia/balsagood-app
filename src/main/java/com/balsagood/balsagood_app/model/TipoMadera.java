package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
     * 
     * "id_tipo_madera" "tipo_descripcion"
     * 1 "L"
     * 2 "P"
     */
@Entity
@Table(name = "tipo_madera")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoMadera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_madera")
    private Integer idTipoMadera;

    @Column(name = "tipo_descripcion", nullable = false)
    private String tipoDescripcion;
}