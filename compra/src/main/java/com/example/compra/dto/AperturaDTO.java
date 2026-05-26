package com.example.compra.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AperturaDTO {
    private Long id;
    private Long jugadorId;
    private Long suministroId;
    private String suministroNombre;
    private LocalDateTime fecha;
    private String cartasObtenidas;
}
