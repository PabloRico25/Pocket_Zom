package com.example.mazo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MazoCartaDTO {
    private Long id;

    @NotBlank(message = "El código de la carta es obligatorio")
    @Size(max = 20, message = "El código no puede superar 20 caracteres")
    private String codigoCarta;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
}