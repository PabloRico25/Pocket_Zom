package com.example.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CartaUsuarioDTO {
    private Long id;

    @NotBlank(message = "El código de la carta es obligatorio")
    @Size(max = 20, message = "El código de carta no puede superar 20 caracteres")
    private String codigoCarta;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    private Boolean esFavorita;

    private LocalDateTime fechaAdquisicion;
}