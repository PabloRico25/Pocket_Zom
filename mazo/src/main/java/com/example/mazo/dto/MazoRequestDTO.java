package com.example.mazo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MazoRequestDTO {
    @NotBlank
    private String nombre;
    private Boolean esActivo;
}
