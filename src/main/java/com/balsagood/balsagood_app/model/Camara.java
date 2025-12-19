package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "camara")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Camara {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camara")
    private Integer idCamara;

    @Column(name = "camara_descripcion", nullable = false, length = 200)
    private String camaraDescripcion;

    @Column(name = "camara_capacidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal camaraCapacidad;
}
