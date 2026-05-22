package com.example.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InventarioResponseDTO {
    private Long id;
    private Long jugadorId;
    private LocalDateTime fechaCreacion;
}