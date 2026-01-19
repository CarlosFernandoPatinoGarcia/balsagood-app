package com.balsagood.balsagood_app.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "historial_pallets_secos")
@NoArgsConstructor
@AllArgsConstructor
public class HistorialPalletsSecando {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial_pallets_secos")
    private Long id;

    @Column(name = "hist_lote_codigo")
    private String histLoteCodigo;

    @Column(name = "hist_num_viaje")
    private Integer histNumViaje;

    @Column(name = "hist_pallet_numero")
    private Integer histPalletNumero;

    @Column(name = "hist_lote_fecha_inicio")
    private LocalDate histLoteFechaInicio;

    @Column(name = "hist_lote_fecha_fin")
    private LocalDate histLoteFechaFin;

    @Column(name = "hist_camara_descripcion")
    private String histCamaraDescripcion;

    @Column(name = "hist_bft_total_lote")
    private Double histBftTotalLote;

    @Column(name = "hist_bft_lote_seco")
    private Double histBftLoteSeco;
}
