package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "items_pallet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Integer idItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pallet", nullable = false)
    private PalletVerde palletVerde;

    @Column(name = "largo", precision = 10, scale = 4)
    private BigDecimal largo;

    @Column(name = "espesor", precision = 10, scale = 4)
    private BigDecimal espesor;

    @Column(name = "cantidad")
    private BigDecimal cantidad;

    @Column(name = "es_castigada")
    private Boolean esCastigada;

    @Column(name = "largo_original", precision = 10, scale = 4)
    private BigDecimal largoOriginal;

    @Column(name = "bft_recibido", precision = 12, scale = 4)
    private BigDecimal bftRecibido;

    @Column(name = "bft_aceptado", precision = 12, scale = 4)
    private BigDecimal bftAceptado;
}
