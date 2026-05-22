package com.example.compra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuministroResponseDTO {
    private Long id;
    private String nombre;
    private Integer costo;
    private Integer cantidadCartas;
    private String probabilidades;
}