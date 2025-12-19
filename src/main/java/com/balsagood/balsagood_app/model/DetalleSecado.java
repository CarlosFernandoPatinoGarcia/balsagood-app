package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_secado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleSecado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_secado")
    private Integer idDetalleSecado;

    @ManyToOne
    @JoinColumn(name = "id_pallet", nullable = false)
    private PalletVerde palletVerde;

    @ManyToOne
    @JoinColumn(name = "id_lote", nullable = false)
    private LoteSecado loteSecado;

    @Column(name = "bft_lote_post_secado", nullable = false, precision = 12, scale = 4)
    private BigDecimal bftLotePostSecado;
}
