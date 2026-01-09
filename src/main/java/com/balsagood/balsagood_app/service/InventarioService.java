package com.balsagood.balsagood_app.service;

import com.balsagood.balsagood_app.dto.RegistroInventarioBloquesDTO;
import com.balsagood.balsagood_app.dto.RegistroInventarioPalletsDTO;
import com.balsagood.balsagood_app.repository.BloqueRepository;
import com.balsagood.balsagood_app.repository.ItemPalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    @Autowired
    private ItemPalletRepository itemPalletRepository;

    @Autowired
    private BloqueRepository bloqueRepository;

    public List<RegistroInventarioPalletsDTO> getReportePallets() {
        return itemPalletRepository.obtenerReportePallets();
    }

    public List<RegistroInventarioBloquesDTO> getReporteBloques() {
        return bloqueRepository.obtenerReporteBloques();
    }
}
