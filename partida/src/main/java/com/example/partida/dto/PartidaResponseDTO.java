package com.example.partida.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PartidaResponseDTO {
    private Long id;
    private Long jugador1Id;
    private Long jugador2Id;
    private Long mazoJ1Id;
    private Long mazoJ2Id;
    private Long ganadorId;
    private String estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}