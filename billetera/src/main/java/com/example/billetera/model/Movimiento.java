package com.example.billetera.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Long idTransaccion;

    @NotNull(message = "El ID de la cartera es obligatorio")
    @Column(name = "id_cartera", nullable = false)
    private Long idCartera;

    // INGRESO suma al saldo, EGRESO resta
    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "INGRESO|EGRESO", message = "El tipo debe ser INGRESO o EGRESO")
    @Column(name = "tipo", nullable = false, length = 10)
    private String tipo;

    @NotNull(message = "El monto es obligatorio")
    @Min(value = 1, message = "El monto debe ser al menos 1")
    @Column(name = "monto", nullable = false)
    private Integer monto;

    @NotBlank(message = "El concepto es obligatorio")
    @Size(max = 100, message = "El concepto no puede superar 100 caracteres")
    @Column(name = "concepto", nullable = false, length = 100)
    private String concepto;

    @Column(name = "fecha")
    private LocalDateTime fecha = LocalDateTime.now();
}
