package com.example.billetera.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MovimientoRequestDTO {
    @NotBlank
    private String tipo;
    @Min(1)
    private Integer monto;
    @NotBlank
    private String concepto;
}