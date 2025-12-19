package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumo_pallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumoPallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consumo")
    private Integer idConsumo;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenTaller ordenTaller;

    @ManyToOne
    @JoinColumn(name = "id_pallet", nullable = false)
    private PalletVerde palletVerde;

    @Column(name = "consumo_hora")
    private LocalDateTime consumoHora;

    @PrePersist
    protected void onCreate() {
        if (consumoHora == null) {
            consumoHora = LocalDateTime.now();
        }
    }
}
