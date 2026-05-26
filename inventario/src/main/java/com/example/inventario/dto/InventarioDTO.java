package com.example.inventario.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InventarioDTO {
    private Long id;
    private Long jugadorId;
    private LocalDateTime fechaCreacion;
}