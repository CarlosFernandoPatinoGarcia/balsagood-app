package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Calificaciones_Pallet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionPallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion")
    private Integer idCalificacion;

    @ManyToOne
    @JoinColumn(name = "id_pallet", nullable = false)
    private PalletVerde palletVerde;

    @Column(name = "calificacion_fecha")
    private LocalDateTime calificacionFecha;

    @Column(name = "calificacion_valor", nullable = false, precision = 10, scale = 3)
    private BigDecimal calificacionValor;

    @Column(name = "calificador_usuario", length = 50)
    private String calificadorUsuario;

    @Column(name = "calificacion_motivo", length = 255)
    private String calificacionMotivo;
}
