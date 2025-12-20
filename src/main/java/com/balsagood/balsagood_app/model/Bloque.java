package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "bloques")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bloque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bloque")
    private Integer idBloque;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenTaller ordenTaller;

    @ManyToOne
    @JoinColumn(name = "id_cuerpo", nullable = false)
    private Cuerpo cuerpo;

    @Column(name = "b_largo", nullable = false, precision = 10, scale = 4)
    private BigDecimal bLargo;

    @Column(name = "b_ancho", nullable = false, precision = 10, scale = 4)
    private BigDecimal bAncho;

    @Column(name = "b_alto", nullable = false, precision = 10, scale = 4)
    private BigDecimal bAlto;

    @Column(name = "b_bft_final", nullable = false, precision = 12, scale = 4)
    private BigDecimal bBftFinal;

    @Column(name = "b_peso_sin_cola", nullable = false, precision = 10, scale = 4)
    private BigDecimal bPesoSinCola;

    @Column(name = "b_peso_con_cola", precision = 10, scale = 4)
    private BigDecimal bPesoConCola;

    @Column(name = "b_codigo", insertable = false, updatable = false)
    private Long bCodigo;

    @Column(name = "b_estado", length = 50)
    private String estado;
}
