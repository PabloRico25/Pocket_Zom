package com.example.billetera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MovimientoResponseDTO {
    private String idTransaccion;
    private String tipo;
    private Integer monto;
    private String concepto;
    private LocalDateTime fecha;
}