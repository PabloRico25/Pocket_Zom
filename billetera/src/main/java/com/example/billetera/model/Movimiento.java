package com.example.billetera.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Data
@NoArgsConstructor
public class Movimiento {
    @Id
    @NotBlank(message = "El ID de transacción es obligatorio")
    private String idTransaccion;

    @NotNull(message = "El ID de la cartera es obligatorio")
    private Long carteraId;

    @NotNull(message = "El monto es obligatorio")
    @Min(value = 1, message = "El monto debe ser al menos 1")
    private Integer monto;

    @NotBlank(message = "El concepto es obligatorio")
    @Size(max = 100, message = "El concepto no puede superar 100 caracteres")
    private String concepto;

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDateTime fecha;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "INGRESO|EGRESO", message = "El tipo debe ser INGRESO o EGRESO")
    private String tipo;

    @Column(name = "tipo_movimiento", nullable = false)
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Size(max = 15)
    private String tipoMovimiento;

    @Column(nullable = false, length = 40)
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 40)
    private String descripcion;

    @Column(name = "billeteras_id_billetera", nullable = false, length = 30)
    @NotBlank(message = "El ID de la billetera es obligatorio")
    @Size(max = 30)
    private String billeterasIdBilletera;
}