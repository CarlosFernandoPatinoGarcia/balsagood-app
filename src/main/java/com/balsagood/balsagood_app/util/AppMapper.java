package com.balsagood.balsagood_app.util;

import com.balsagood.balsagood_app.dto.*;
import com.balsagood.balsagood_app.model.*;
import org.springframework.stereotype.Component;

@Component
public class AppMapper {

    // --- Proveedor ---
    public ProveedorDTO toProveedorDTO(Proveedor proveedor) {
        if (proveedor == null)
            return null;
        return new ProveedorDTO(proveedor.getIdProveedor(), proveedor.getProvNombre());
    }

    public Proveedor toProveedorEntity(ProveedorDTO dto) {
        if (dto == null)
            return null;
        return new Proveedor(dto.getIdProveedor(), dto.getProvNombre());
    }

    // --- Recepcion ---
    public RecepcionDTO toRecepcionDTO(Recepcion recepcion) {
        if (recepcion == null)
            return null;
        return new RecepcionDTO(
                recepcion.getIdRecepcion(),
                toProveedorDTO(recepcion.getProveedor()),
                recepcion.getNumViaje(),
                recepcion.getFechaIngreso());
    }

    public Recepcion toRecepcionEntity(RecepcionDTO dto) {
        if (dto == null)
            return null;
        Recepcion recepcion = new Recepcion();
        recepcion.setIdRecepcion(dto.getIdRecepcion());
        recepcion.setNumViaje(dto.getNumViaje());
        recepcion.setFechaIngreso(dto.getFechaIngreso());
        recepcion.setProveedor(toProveedorEntity(dto.getProveedor()));
        return recepcion;
    }

    // --- Cuerpo ---
    public CuerpoDTO toCuerpoDTO(Cuerpo cuerpo) {
        if (cuerpo == null)
            return null;
        return new CuerpoDTO(
                cuerpo.getIdCuerpo(),
                cuerpo.getCuerpoAnchoFinal(),
                cuerpo.getCuerpoObservacion());
    }

    public Cuerpo toCuerpoEntity(CuerpoDTO dto) {
        if (dto == null)
            return null;
        return new Cuerpo(
                dto.getIdCuerpo(),
                dto.getCuerpoAnchoFinal(),
                dto.getCuerpoObservacion());
    }

    // --- OrdenTaller ---
    public OrdenTallerDTO toOrdenTallerDTO(OrdenTaller orden) {
        if (orden == null)
            return null;
        return new OrdenTallerDTO(
                orden.getIdOrden(),
                orden.getOrdenFechaInicio(),
                orden.getOrdenFechaFin(),
                orden.getOrdenObservacion());
    }

    public OrdenTaller toOrdenTallerEntity(OrdenTallerDTO dto) {
        if (dto == null)
            return null;
        return new OrdenTaller(
                dto.getIdOrden(),
                dto.getOrdenFechaInicio(),
                dto.getOrdenFechaFin(),
                dto.getOrdenObservacion());
    }

    // --- Camara ---
    public CamaraDTO toCamaraDTO(Camara camara) {
        if (camara == null)
            return null;
        return new CamaraDTO(
                camara.getIdCamara(),
                camara.getCamaraDescripcion(),
                camara.getCamaraCapacidad());
    }

    public Camara toCamaraEntity(CamaraDTO dto) {
        if (dto == null)
            return null;
        return new Camara(
                dto.getIdCamara(),
                dto.getCamaraDescripcion(),
                dto.getCamaraCapacidad());
    }

    // --- Despacho ---
    public DespachoDTO toDespachoDTO(Despacho despacho) {
        if (despacho == null)
            return null;
        return new DespachoDTO(
                despacho.getIdDespacho(),
                despacho.getDespFecha(),
                despacho.getDespObservacion());
    }

    public Despacho toDespachoEntity(DespachoDTO dto) {
        if (dto == null)
            return null;
        Despacho despacho = new Despacho();
        despacho.setIdDespacho(dto.getIdDespacho());
        despacho.setDespFecha(dto.getDespFecha());
        despacho.setDespObservacion(dto.getDespObservacion());
        return despacho;
    }

    // --- PalletVerde ---
    public PalletVerdeDTO toPalletVerdeDTO(PalletVerde pallet) {
        if (pallet == null)
            return null;
        return new PalletVerdeDTO(
                pallet.getIdPallet(),
                toRecepcionDTO(pallet.getRecepcion()),
                pallet.getPalletNumero(),
                pallet.getPalletEmplantillador(),
                pallet.getPalletCantPlantillas(),
                pallet.getPalletAncho(),
                pallet.getPalletEspesor(),
                pallet.getPalletLargo(),
                pallet.getPalletAnchoPlantilla(),
                pallet.getBftVerdeRecibido(),
                pallet.getBftVerdeAceptado(),
                pallet.getPalletEstado(),
                pallet.getPalletObservacion());
    }

    public PalletVerde toPalletVerdeEntity(PalletVerdeDTO dto) {
        if (dto == null)
            return null;
        PalletVerde pallet = new PalletVerde();
        pallet.setIdPallet(dto.getIdPallet());
        pallet.setRecepcion(toRecepcionEntity(dto.getRecepcion()));
        pallet.setPalletNumero(dto.getPalletNumero());
        pallet.setPalletEmplantillador(dto.getPalletEmplantillador());
        pallet.setPalletCantPlantillas(dto.getPalletCantPlantillas());
        pallet.setPalletAncho(dto.getPalletAncho());
        pallet.setPalletEspesor(dto.getPalletEspesor());
        pallet.setPalletLargo(dto.getPalletLargo());
        pallet.setPalletAnchoPlantilla(dto.getPalletAnchoPlantilla());
        pallet.setBftVerdeRecibido(dto.getBftVerdeRecibido());
        pallet.setBftVerdeAceptado(dto.getBftVerdeAceptado());
        pallet.setPalletEstado(dto.getPalletEstado());
        pallet.setPalletObservacion(dto.getPalletObservacion());
        return pallet;
    }

    // --- Bloque ---
    public BloqueDTO toBloqueDTO(Bloque bloque) {
        if (bloque == null)
            return null;
        return new BloqueDTO(
                bloque.getIdBloque(),
                toOrdenTallerDTO(bloque.getOrdenTaller()),
                toCuerpoDTO(bloque.getCuerpo()),
                bloque.getBLargo(),
                bloque.getBAncho(),
                bloque.getBAlto(),
                bloque.getBBftFinal(),
                bloque.getBPesoSinCola(),
                bloque.getBPesoConCola(),
                bloque.getBCodigo(),
                bloque.getEstado());
    }

    public Bloque toBloqueEntity(BloqueDTO dto) {
        if (dto == null)
            return null;
        Bloque bloque = new Bloque();
        bloque.setIdBloque(dto.getIdBloque());
        bloque.setOrdenTaller(toOrdenTallerEntity(dto.getOrdenTaller()));
        bloque.setCuerpo(toCuerpoEntity(dto.getCuerpo()));
        bloque.setBLargo(dto.getBLargo());
        bloque.setBAncho(dto.getBAncho());
        bloque.setBAlto(dto.getBAlto());
        bloque.setBBftFinal(dto.getBBftFinal());
        bloque.setBPesoSinCola(dto.getBPesoSinCola());
        bloque.setBPesoConCola(dto.getBPesoConCola());
        bloque.setEstado(dto.getEstado());
        return bloque;
    }

    // --- LoteSecado ---
    public LoteSecadoDTO toLoteSecadoDTO(LoteSecado lote) {
        if (lote == null)
            return null;
        return new LoteSecadoDTO(
                lote.getIdLote(),
                toCamaraDTO(lote.getCamara()),
                lote.getLoteFechaInicio(),
                lote.getLoteFechaFin(),
                lote.getLoteObservaciones());
    }

    public LoteSecado toLoteSecadoEntity(LoteSecadoDTO dto) {
        if (dto == null)
            return null;
        LoteSecado lote = new LoteSecado();
        lote.setIdLote(dto.getIdLote());
        lote.setCamara(toCamaraEntity(dto.getCamara()));
        lote.setLoteFechaInicio(dto.getLoteFechaInicio());
        lote.setLoteFechaFin(dto.getLoteFechaFin());
        lote.setLoteObservaciones(dto.getLoteObservaciones());
        return lote;
    }

    // --- DetalleSecado ---
    public DetalleSecadoDTO toDetalleSecadoDTO(DetalleSecado detalle) {
        if (detalle == null)
            return null;
        return new DetalleSecadoDTO(
                detalle.getIdDetalleSecado(),
                toPalletVerdeDTO(detalle.getPalletVerde()),
                toLoteSecadoDTO(detalle.getLoteSecado()),
                detalle.getBftLotePostSecado());
    }

    public DetalleSecado toDetalleSecadoEntity(DetalleSecadoDTO dto) {
        if (dto == null)
            return null;
        DetalleSecado detalle = new DetalleSecado();
        detalle.setIdDetalleSecado(dto.getIdDetalleSecado());
        detalle.setPalletVerde(toPalletVerdeEntity(dto.getPalletVerde()));
        detalle.setLoteSecado(toLoteSecadoEntity(dto.getLoteSecado()));
        detalle.setBftLotePostSecado(dto.getBftLotePostSecado());
        return detalle;
    }

    // --- ConsumoPallet ---
    public ConsumoPalletDTO toConsumoPalletDTO(ConsumoPallet consumo) {
        if (consumo == null)
            return null;
        return new ConsumoPalletDTO(
                consumo.getIdConsumo(),
                toOrdenTallerDTO(consumo.getOrdenTaller()),
                toPalletVerdeDTO(consumo.getPalletVerde()),
                consumo.getConsumoHora());
    }

    public ConsumoPallet toConsumoPalletEntity(ConsumoPalletDTO dto) {
        if (dto == null)
            return null;
        ConsumoPallet consumo = new ConsumoPallet();
        consumo.setIdConsumo(dto.getIdConsumo());
        consumo.setOrdenTaller(toOrdenTallerEntity(dto.getOrdenTaller()));
        consumo.setPalletVerde(toPalletVerdeEntity(dto.getPalletVerde()));
        consumo.setConsumoHora(dto.getConsumoHora());
        return consumo;
    }

    // --- DetalleDespacho ---
    public DetalleDespachoDTO toDetalleDespachoDTO(DetalleDespacho detalle) {
        if (detalle == null)
            return null;
        return new DetalleDespachoDTO(
                detalle.getIdDetalleDespacho(),
                toCuerpoDTO(detalle.getCuerpo()),
                toDespachoDTO(detalle.getDespacho()));
    }

    public DetalleDespacho toDetalleDespachoEntity(DetalleDespachoDTO dto) {
        if (dto == null)
            return null;
        DetalleDespacho detalle = new DetalleDespacho();
        detalle.setIdDetalleDespacho(dto.getIdDetalleDespacho());
        detalle.setCuerpo(toCuerpoEntity(dto.getCuerpo()));
        detalle.setDespacho(toDespachoEntity(dto.getDespacho()));
        return detalle;
    }

    // --- CalificacionPallet ---
    public CalificacionPalletDTO toCalificacionPalletDTO(CalificacionPallet calificacion) {
        if (calificacion == null)
            return null;
        return new CalificacionPalletDTO(
                calificacion.getIdCalificacion(),
                toPalletVerdeDTO(calificacion.getPalletVerde()),
                calificacion.getCalificacionFecha(),
                calificacion.getCalificacionValor(),
                calificacion.getCalificadorUsuario(),
                calificacion.getCalificacionMotivo());
    }

    public CalificacionPallet toCalificacionPalletEntity(CalificacionPalletDTO dto) {
        if (dto == null)
            return null;
        CalificacionPallet calificacion = new CalificacionPallet();
        calificacion.setIdCalificacion(dto.getIdCalificacion());
        calificacion.setPalletVerde(toPalletVerdeEntity(dto.getPalletVerde()));
        calificacion.setCalificacionFecha(dto.getCalificacionFecha());
        calificacion.setCalificacionValor(dto.getCalificacionValor());
        calificacion.setCalificadorUsuario(dto.getCalificadorUsuario());
        calificacion.setCalificacionMotivo(dto.getCalificacionMotivo());
        return calificacion;
    }
}
