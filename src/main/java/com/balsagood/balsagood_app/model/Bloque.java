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
    @JoinColumn(name = "id_cuerpo", nullable = true)
    private Cuerpo cuerpo;

    @Column(name = "b_largo", nullable = false, precision = 10, scale = 4)
    private BigDecimal bloqueLargo;

    @Column(name = "b_ancho", nullable = true, precision = 10, scale = 4)
    private BigDecimal bloqueAncho;

    @Column(name = "b_alto", nullable = true, precision = 10, scale = 4)
    private BigDecimal bloqueAlto;

    @Column(name = "b_bft_final", nullable = true, precision = 12, scale = 4)
    private BigDecimal bloqueBftFinal;

    @Column(name = "b_peso_sin_cola", nullable = false, precision = 10, scale = 4)
    private BigDecimal bloquePesoSinCola;

    @Column(name = "b_peso_con_cola", precision = 10, scale = 4)
    private BigDecimal bloquePesoConCola;

    @Column(name = "b_codigo", nullable = false, precision = 10, scale = 4)
    private Long bloqueCodigo;

    @Column(name = "b_estado", length = 50)
    private String estado;
}
