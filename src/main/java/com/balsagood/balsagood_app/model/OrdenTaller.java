package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "ordenes_taller")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenTaller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Integer idOrden;

    @Column(name = "orden_fecha_inicio", nullable = false)
    private LocalDate ordenFechaInicio;

    @Column(name = "orden_fecha_fin")
    private LocalDate ordenFechaFin;

    @Column(name = "orden_observacion", columnDefinition = "TEXT")
    private String ordenObservacion;
}
