package com.example.mazo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MazoCartaDTO {
    private Long id;
    @NotBlank(message = "El código de la carta es obligatorio")
    private String codigoCarta;
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
}