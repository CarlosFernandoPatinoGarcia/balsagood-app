package com.balsagood.balsagood_app.dto.dashboard;

import java.util.List;

import com.balsagood.balsagood_app.dto.movil.BloqueDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloquesPresentadosDTO {
    private List<BloqueDTO> bloques; // Lista de bloques
}
