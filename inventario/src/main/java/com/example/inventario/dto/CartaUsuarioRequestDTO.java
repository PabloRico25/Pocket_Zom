package com.example.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CartaUsuarioRequestDTO {
    @NotBlank
    private String codigoCarta;
    @Min(1)
    private Integer cantidad;
    private Boolean esFavorita;
}
