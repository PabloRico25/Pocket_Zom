package com.example.publicacion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PublicacionRequestDTO {
    @NotBlank
    private String codigoCarta;
    @NotNull
    @Min(1)
    private Integer precio;
}