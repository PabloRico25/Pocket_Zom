package com.example.rango.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClasificacionRequestDTO {
    @NotNull
    private Boolean esVictoria;   // true = ganó, false = perdió
    private Integer cambioElo;    // opcional, si no se envía se calcula automático
}