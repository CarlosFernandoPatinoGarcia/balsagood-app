package com.balsagood.balsagood_app.dto;

import lombok.Data;
import java.util.List;

@Data
public class AgrupacionRequest {
    private List<Integer> idsBloques;
    private String observacion;
}