package com.example.compra.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SuministroRequestDTO {
    @NotBlank
    private String nombre;
    @NotNull @Min(0)
    private Integer costo;
    @NotNull @Min(1)
    private Integer cantidadCartas;
    private String probabilidades;
}