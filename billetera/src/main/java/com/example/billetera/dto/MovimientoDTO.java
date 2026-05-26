package com.example.billetera.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovimientoDTO {
    private String idTransaccion;   // se genera en el servidor

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "INGRESO|EGRESO", message = "El tipo debe ser INGRESO o EGRESO")
    private String tipo;

    @NotNull(message = "El monto es obligatorio")
    @Min(value = 1, message = "El monto debe ser al menos 1")
    private Integer monto;

    @NotBlank(message = "El concepto es obligatorio")
    private String concepto;

    private LocalDateTime fecha;   // se asigna en el servidor
}