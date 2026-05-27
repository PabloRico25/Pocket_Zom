package com.example.billetera.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MovimientoDTO {
    private String idTransaccion;
    @NotBlank
    private String tipo;
    @Min(1)
    private Integer monto;
    @NotBlank
    private String concepto;
    private LocalDateTime fecha;
}