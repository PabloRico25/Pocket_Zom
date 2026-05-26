package com.example.rango.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClasificacionDTO {
    private Long id;
    @NotNull
    private Long jugadorId;
    private Integer puntosElo;
    private Integer victorias;
    private Integer derrotas;
    private String rangoActual;
}
