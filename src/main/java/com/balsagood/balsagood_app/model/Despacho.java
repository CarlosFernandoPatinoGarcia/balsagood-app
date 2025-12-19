package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "despachos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despacho")
    private Integer idDespacho;

    @Column(name = "desp_fecha")
    private LocalDateTime despFecha;

    @Column(name = "desp_observacion", columnDefinition = "TEXT")
    private String despObservacion;

    @PrePersist
    protected void onCreate() {
        if (despFecha == null) {
            despFecha = LocalDateTime.now();
        }
    }
}
