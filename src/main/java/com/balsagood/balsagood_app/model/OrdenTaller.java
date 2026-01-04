package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime ordenFechaInicio;

    @Column(name = "orden_fecha_fin")
    private LocalDateTime ordenFechaFin;

    @Column(name = "orden_observacion", columnDefinition = "TEXT")
    private String ordenObservacion;

    @OneToMany(mappedBy = "ordenTaller")
    private List<Bloque> bloques;
}
