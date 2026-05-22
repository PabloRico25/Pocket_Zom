package com.example.compra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AperturaResponseDTO {
    private Long id;
    private Long jugadorId;
    private Long suministroId;
    private String suministroNombre;
    private LocalDateTime fecha;
    private String cartasObtenidas;
}