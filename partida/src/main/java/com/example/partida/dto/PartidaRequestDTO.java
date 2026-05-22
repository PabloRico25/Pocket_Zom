package com.example.partida.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartidaRequestDTO {
    @NotNull
    private Long jugador2Id;
    @NotNull
    private Long mazoJ1Id;
    @NotNull
    private Long mazoJ2Id;
}