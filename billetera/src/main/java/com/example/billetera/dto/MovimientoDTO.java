package com.example.billetera.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDTO {

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "INGRESO|EGRESO", message = "El tipo debe ser INGRESO o EGRESO")
    private String tipo;

    @NotNull(message = "El monto es obligatorio")
    @Min(value = 1, message = "El monto debe ser al menos 1")
    private Integer monto;

    @NotBlank(message = "El concepto es obligatorio")
    @Size(max = 100, message = "El concepto no puede superar 100 caracteres")
    private String concepto;
}

