package com.example.rango.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClasificacionResponseDTO {
    private Long id;
    private Long jugadorId;
    private Integer puntosElo;
    private Integer victorias;
    private Integer derrotas;
    private String rangoActual;
}