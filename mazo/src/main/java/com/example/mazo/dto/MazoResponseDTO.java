package com.example.mazo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MazoResponseDTO {
    private Long id;
    private Long jugadorId;
    private String nombre;
    private LocalDateTime fechaCreacion;
    private Boolean esActivo;
}