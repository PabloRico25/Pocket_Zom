package com.example.logros.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogroResponseDTO {
    private String idLogro;
    private String nombre;
    private String descripcion;
    private String condicionTipo;
    private Integer condicionValor;
    private Integer recompensaMonedas;
    private Integer recompensaExp;
}