package com.balsagood.balsagood_app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePalletsSecosDTO {

    /*
     * -- Pallets Secos
     * select ls.lote_codigo, (CONCAT(r.num_viaje, ' ', p.pallet_numero)) as
     * codigo_pallet, ls.lote_fecha_inicio, ls.lote_fecha_fin, c.camara_descripcion,
     * ls.bft_total_lote, ls.bft_lote_seco
     * from lotes_secado ls
     * inner join detalle_secado ds on ls.id_lote = ds.id_lote
     * inner join pallets_verdes p on p.id_pallet = ds.id_pallet
     * inner join recepcion r on r.id_recepcion = p.id_recepcion
     * inner join camara c on c.id_camara = ls.id_camara
     * where p.pallet_estado = 'SE'
     */

    private String loteCodigo; // Codigo del lote
    private String codigoPallet; // Concatenacion del numero de viaje y el numero del pallet
    private LocalDateTime loteFechaInicio; // Fecha de ingreso del lote a la camara
    private LocalDateTime loteFechaFin; // Fecha de salida del lote de la camara
    private String camaraDescripcion; // Descripcion de la camara
    private Double bftTotalLote; // BFT total del lote
    private Double bftLoteSeco; // BFT seco del lote
}
