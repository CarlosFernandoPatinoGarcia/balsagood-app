package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "pallets_verdes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PalletVerde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pallet")
    private Integer idPallet;

    @ManyToOne
    @JoinColumn(name = "id_recepcion", nullable = false)
    private Recepcion recepcion;

    @Column(name = "pallet_numero", nullable = false)
    private Integer palletNumero;

    @Column(name = "pallet_emplantillador", length = 100)
    private String palletEmplantillador;

    @Column(name = "pallet_cant_plantillas")
    private Integer palletCantPlantillas;

    @Column(name = "pallet_ancho", nullable = false, precision = 10, scale = 4)
    private BigDecimal palletAncho;

    @Column(name = "pallet_espesor", nullable = false, precision = 10, scale = 4)
    private BigDecimal palletEspesor;

    @Column(name = "pallet_largo", nullable = false, precision = 10, scale = 4)
    private BigDecimal palletLargo;

    @Column(name = "pallet_ancho_plantilla", nullable = false, precision = 10, scale = 4)
    private BigDecimal palletAnchoPlantilla;

    @Column(name = "bft_verde_recibido", nullable = false, precision = 12, scale = 4)
    private BigDecimal bftVerdeRecibido;

    @Column(name = "bft_verde_aceptado", nullable = false, precision = 12, scale = 4)
    private BigDecimal bftVerdeAceptado;

    @Column(name = "pallet_estado", nullable = false, length = 50)
    private String palletEstado;

    @Column(name = "pallet_observacion", columnDefinition = "TEXT")
    private String palletObservacion;

    @OneToMany(mappedBy = "palletVerde", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<ItemPallet> items;

    @PrePersist
    protected void onCreate() {
        if (palletEstado == null) {
            palletEstado = "MADERA VERDE";
        }
    }
}
