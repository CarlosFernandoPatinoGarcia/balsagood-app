package com.balsagood.balsagood_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuerpoDTO {
    private Integer idCuerpo;
    private List<Integer> idsBloques;
    private BigDecimal largoFinal;
    private String observacion;
}
