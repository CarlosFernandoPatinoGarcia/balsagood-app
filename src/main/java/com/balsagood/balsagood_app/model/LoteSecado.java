package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lotes_secado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteSecado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Integer idLote;

    @ManyToOne
    @JoinColumn(name = "id_camara", nullable = false)
    private Camara camara;

    @Column(name = "lote_fecha_inicio", nullable = false)
    private LocalDateTime loteFechaInicio;

    @Column(name = "lote_fecha_fin")
    private LocalDateTime loteFechaFin;

    @Column(name = "lote_observaciones", columnDefinition = "TEXT")
    private String loteObservaciones;

    @Column(name = "bft_total_lote", precision = 12, scale = 4)
    private BigDecimal bftTotalLote;
}
