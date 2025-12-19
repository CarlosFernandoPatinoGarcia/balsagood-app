package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "cuerpos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuerpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuerpo")
    private Integer idCuerpo;

    @Column(name = "cuerpo_ancho_final", nullable = false, precision = 10, scale = 4)
    private BigDecimal cuerpoAnchoFinal;

    @Column(name = "cuerpo_observacion", columnDefinition = "TEXT")
    private String cuerpoObservacion;
}
