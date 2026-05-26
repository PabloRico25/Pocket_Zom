package com.example.logros.dto;

import lombok.Data;

@Data
public class VerificarProgresoDTO {
    private Long jugadorId;
    private String condicionTipo;
    private Integer valorActual;
}
