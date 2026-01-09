package com.balsagood.balsagood_app.controller;

import com.balsagood.balsagood_app.dto.TipoMaderaDTO;

import com.balsagood.balsagood_app.service.TipoMaderaService;
import com.balsagood.balsagood_app.util.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos-madera")
public class TipoMaderaController {

    @Autowired
    private TipoMaderaService tipoMaderaService;

    @Autowired
    private AppMapper appMapper;

    @GetMapping
    public List<TipoMaderaDTO> getAll() {
        // Return a list of DTOs
        return tipoMaderaService.findAll().stream()
                .map(appMapper::toTipoMaderaDTO)
                .collect(Collectors.toList());
    }
}
