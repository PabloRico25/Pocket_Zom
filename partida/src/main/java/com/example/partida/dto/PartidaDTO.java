package com.example.partida.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartidaDTO {
    private Long id;
    @NotNull
    private Long jugador1Id;
    @NotNull
    private Long jugador2Id;
    @NotNull
    private Long mazoJ1Id;
    @NotNull
    private Long mazoJ2Id;
    private Long ganadorId;
    private String estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
