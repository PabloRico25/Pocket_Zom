package com.example.inventario.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InventarioDTO {
    private Long id;
    private Long jugadorId;
    private LocalDateTime fechaCreacion;
}