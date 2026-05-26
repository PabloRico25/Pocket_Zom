package com.example.billetera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO {
    private String idTransaccion;
    private String tipo;
    private Integer monto;
    private String concepto;
    private LocalDateTime fecha;
}
