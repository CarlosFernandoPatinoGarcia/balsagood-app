package com.balsagood.balsagood_app.util;

import com.balsagood.balsagood_app.dto.*;
import com.balsagood.balsagood_app.model.*;

import java.util.stream.Collectors;

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

    // --- TipoMadera ---
    public TipoMaderaDTO toTipoMaderaDTO(TipoMadera tipoMadera) {
        if (tipoMadera == null)
            return null;
        return new TipoMaderaDTO(
                tipoMadera.getIdTipoMadera(),
                tipoMadera.getTipoDescripcion());
    }

    public TipoMadera toTipoMaderaEntity(TipoMaderaDTO dto) {
        if (dto == null)
            return null;
        return new TipoMadera(
                dto.getIdTipoMadera(),
                dto.getTipoDescripcion());
    }

    // --- Cuerpo ---
    // --- Cuerpo ---
    public CuerpoDTO toCuerpoDTO(Cuerpo cuerpo) {
        if (cuerpo == null)
            return null;
        return new CuerpoDTO(
                cuerpo.getIdCuerpo(),
                null, // idsBloques no se mapean desde el cuerpo base (se llenarían si es necesario)
                cuerpo.getCuerpoLargoFinal(),
                cuerpo.getCuerpoObservacion());
    }

    public Cuerpo toCuerpoEntity(CuerpoDTO dto) {
        if (dto == null)
            return null;
        return new Cuerpo(
                dto.getIdCuerpo(),
                dto.getLargoFinal(),
                dto.getObservacion());
    }

    // --- OrdenTaller ---
    public OrdenTallerDTO toOrdenTallerDTO(OrdenTaller orden) {
        if (orden == null)
            return null;

        OrdenTallerDTO dto = new OrdenTallerDTO(
                orden.getIdOrden(),
                orden.getOrdenFechaInicio(),
                orden.getOrdenFechaFin(),
                orden.getOrdenObservacion(),
                null);

        // Mapear los bloques hijos si existen
        if (orden.getBloques() != null) {
            java.util.List<BloqueDTO> bloquesDTO = orden.getBloques().stream()
                    .map(bloque -> {
                        return new BloqueDTO(
                                bloque.getIdBloque(),
                                toTipoMaderaDTO(bloque.getTipoMadera()),
                                null, // Romper ciclo: no mapear la orden de vuelta
                                toCuerpoDTO(bloque.getCuerpo()),
                                bloque.getBloqueLargo(),
                                bloque.getBloqueAncho(),
                                bloque.getBloqueAlto(),
                                bloque.getBloqueBftFinal(),
                                bloque.getBloquePesoSinCola(),
                                bloque.getBloquePesoConCola(),
                                bloque.getBloqueCodigo(),
                                bloque.getEstado());
                    })
                    .collect(Collectors.toList());

            dto.setBloques(bloquesDTO);
        }

        return dto;
    }

    public OrdenTaller toOrdenTallerEntity(OrdenTallerDTO dto) {
        if (dto == null)
            return null;
        OrdenTaller orden = new OrdenTaller();
        orden.setIdOrden(dto.getIdOrden());
        orden.setOrdenFechaInicio(dto.getOrdenFechaInicio());
        orden.setOrdenFechaFin(dto.getOrdenFechaFin());
        orden.setOrdenObservacion(dto.getOrdenObservacion());
        return orden;
    }

    // --- Camara ---
    public CamaraDTO toCamaraDTO(Camara camara) {
        if (camara == null)
            return null;
        return new CamaraDTO(
                camara.getIdCamara(),
                camara.getCamaraDescripcion(),
                camara.getCamaraCapacidad(),
                camara.getCapacidadDisponible());
    }

    public Camara toCamaraEntity(CamaraDTO dto) {
        if (dto == null)
            return null;
        return new Camara(
                dto.getIdCamara(),
                dto.getCamaraDescripcion(),
                dto.getCamaraCapacidad(),
                dto.getCapacidadDisponible());
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
                toTipoMaderaDTO(pallet.getTipoMadera()),
                pallet.getPalletNumero(),
                pallet.getPalletEmplantillador(),
                pallet.getPalletAnchoPlantilla(),
                pallet.getBftVerdeRecibido(),
                pallet.getBftVerdeAceptado(),
                pallet.getBftVerdeSeco(),
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
        pallet.setPalletAnchoPlantilla(dto.getPalletAnchoPlantilla());
        pallet.setBftVerdeRecibido(dto.getBftVerdeRecibido());
        pallet.setBftVerdeAceptado(dto.getBftVerdeAceptado());
        pallet.setBftVerdeSeco(dto.getBftVerdeSeco());
        pallet.setPalletEstado(dto.getPalletEstado());
        pallet.setPalletObservacion(dto.getPalletObservacion());
        return pallet;
    }

    // --- Bloque ---
    public BloqueDTO toBloqueDTO(Bloque bloque) {
        if (bloque == null)
            return null;

        OrdenTallerDTO ordenDto = toOrdenTallerDTO(bloque.getOrdenTaller());

        if (ordenDto != null) {
            ordenDto.setBloques(null);
        }

        return new BloqueDTO(
                bloque.getIdBloque(),
                toTipoMaderaDTO(bloque.getTipoMadera()),
                ordenDto,
                toCuerpoDTO(bloque.getCuerpo()),
                bloque.getBloqueLargo(),
                bloque.getBloqueAncho(),
                bloque.getBloqueAlto(),
                bloque.getBloqueBftFinal(),
                bloque.getBloquePesoSinCola(),
                bloque.getBloquePesoConCola(),
                bloque.getBloqueCodigo(),
                bloque.getEstado());
    }

    public Bloque toBloqueEntity(BloqueDTO dto) {
        if (dto == null)
            return null;
        Bloque bloque = new Bloque();
        bloque.setIdBloque(dto.getIdBloque());
        bloque.setTipoMadera(toTipoMaderaEntity(dto.getTipoMadera()));
        bloque.setOrdenTaller(toOrdenTallerEntity(dto.getOrdenTaller()));
        bloque.setCuerpo(toCuerpoEntity(dto.getCuerpo()));
        bloque.setBloqueLargo(dto.getBloqueLargo());
        bloque.setBloqueAncho(dto.getBloqueAncho());
        bloque.setBloqueAlto(dto.getBloqueAlto());
        bloque.setBloqueBftFinal(dto.getBloqueBftFinal());
        bloque.setBloquePesoSinCola(dto.getBloquePesoSinCola());
        bloque.setBloquePesoConCola(dto.getBloquePesoConCola());
        bloque.setBloqueCodigo(dto.getBloqueCodigo());
        bloque.setEstado(dto.getEstado());
        return bloque;
    }

    // --- LoteSecado ---
    public LoteSecadoDTO toLoteSecadoDTO(LoteSecado lote) {
        if (lote == null)
            return null;

        String estado = "DF";
        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        if (lote.getFechaDespacho() != null) {
            estado = "DES";
        } else if (lote.getBftLoteSeco() != null) {
            estado = "FIN";
        } else if (lote.getLoteFechaFin() != null && now.isAfter(lote.getLoteFechaFin())) {
            estado = "OK";
        } else if (lote.getLoteFechaInicio() != null && now.isAfter(lote.getLoteFechaInicio())
                && (lote.getLoteFechaFin() == null || now.isBefore(lote.getLoteFechaFin()))) {
            estado = "SE";
        }

        return new LoteSecadoDTO(
                lote.getIdLote(),
                toCamaraDTO(lote.getCamara()),
                lote.getLoteCodigo(),
                lote.getLoteFechaInicio(),
                lote.getLoteFechaFin(),
                lote.getLoteObservaciones(),
                lote.getBftTotalLote(),
                lote.getBftLoteSeco(),
                lote.getFechaDespacho(),
                estado);
    }

    public LoteSecado toLoteSecadoEntity(LoteSecadoDTO dto) {
        if (dto == null)
            return null;
        LoteSecado lote = new LoteSecado();
        lote.setIdLote(dto.getIdLote());
        lote.setCamara(toCamaraEntity(dto.getCamara()));
        lote.setLoteCodigo(dto.getLoteCodigo());
        lote.setLoteFechaInicio(dto.getLoteFechaInicio());
        lote.setLoteFechaFin(dto.getLoteFechaFin());
        lote.setLoteObservaciones(dto.getLoteObservaciones());
        lote.setBftTotalLote(dto.getBftTotalLote());
        lote.setBftLoteSeco(dto.getBftLoteSeco());
        lote.setFechaDespacho(dto.getFechaDespacho());
        return lote;
    }

    // --- DetalleSecado ---
    public DetalleSecadoDTO toDetalleSecadoDTO(DetalleSecado detalle) {
        if (detalle == null)
            return null;
        return new DetalleSecadoDTO(
                detalle.getIdDetalleSecado(),
                toPalletVerdeDTO(detalle.getPalletVerde()),
                toLoteSecadoDTO(detalle.getLoteSecado()));
    }

    public DetalleSecado toDetalleSecadoEntity(DetalleSecadoDTO dto) {
        if (dto == null)
            return null;
        DetalleSecado detalle = new DetalleSecado();
        detalle.setIdDetalleSecado(dto.getIdDetalleSecado());
        detalle.setPalletVerde(toPalletVerdeEntity(dto.getPalletVerde()));
        detalle.setLoteSecado(toLoteSecadoEntity(dto.getLoteSecado()));
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

}
