package com.example.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CartaUsuarioResponseDTO {
    private Long id;
    private String codigoCarta;
    private Integer cantidad;
    private LocalDateTime fechaAdquisicion;
    private Boolean esFavorita;
}