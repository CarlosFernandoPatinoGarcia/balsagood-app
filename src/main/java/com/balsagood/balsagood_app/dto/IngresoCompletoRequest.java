package com.balsagood.balsagood_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class IngresoCompletoRequest {

    @JsonProperty("num_viaje")
    private Integer numViaje;

    @JsonProperty("fecha_ingreso")
    private LocalDate fechaIngreso;

    @JsonProperty("prov_nombre")
    private String provNombre;

    @JsonProperty("pallet_numero")
    private Integer palletNumero;

    @JsonProperty("pallet_emplantillador")
    private String palletEmplantillador;

    @JsonProperty("dimensiones")
    private Dimensiones dimensiones;

    @JsonProperty("calificaciones")
    private List<Calificacion> calificaciones;

    @Data
    public static class Dimensiones {
        @JsonProperty("largo")
        private BigDecimal largo;

        @JsonProperty("ancho_plantilla")
        private BigDecimal anchoPlantilla;

        @JsonProperty("espesor")
        private BigDecimal espesor;

        @JsonProperty("cantidad_plantilla")
        private Integer cantidadPlantilla;
    }

    @Data
    public static class Calificacion {
        @JsonProperty("valor")
        private BigDecimal valor;

        @JsonProperty("motivo")
        private String motivo;

        @JsonProperty("largo")
        private BigDecimal largo;

        @JsonProperty("espesor")
        private BigDecimal espesor;

        @JsonProperty("cantidad")
        private Integer cantidad;

        @JsonProperty("es_castigada")
        private Boolean esCastigada;

        @JsonProperty("largo_original")
        private BigDecimal largoOriginal;
    }
}
