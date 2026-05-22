package com.example.mazo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MazoCartaRequestDTO {
    @NotBlank
    private String codigoCarta;
    @Min(1)
    private Integer cantidad;
}