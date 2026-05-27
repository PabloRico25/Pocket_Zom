package com.example.publicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDTO {
    private String tipo;
    private Integer monto;
    private String concepto;
}