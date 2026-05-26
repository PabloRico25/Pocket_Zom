package com.example.billetera.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "carteras")
@Data
public class Cartera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del jugador es obligatorio")
    private Long jugadorId;

    @Min(value = 0, message = "El saldo no puede ser negativo")
    private Integer saldo = 0;

    @PastOrPresent(message = "La fecha de actualización no puede ser futura")
    private LocalDateTime ultimaActualizacion = LocalDateTime.now();
}