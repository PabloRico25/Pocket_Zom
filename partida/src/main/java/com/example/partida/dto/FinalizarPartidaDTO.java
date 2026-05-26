package com.example.partida.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FinalizarPartidaDTO {
    @NotNull
    private Long ganadorId;
}
