package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_despacho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_despacho")
    private Integer idDetalleDespacho;

    @ManyToOne
    @JoinColumn(name = "id_cuerpo", nullable = false)
    private Cuerpo cuerpo;

    @ManyToOne
    @JoinColumn(name = "id_despacho", nullable = false)
    private Despacho despacho;
}
