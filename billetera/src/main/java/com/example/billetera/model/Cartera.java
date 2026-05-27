package com.example.billetera.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "carteras")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cartera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cartera")
    private Long idCartera;

    @NotNull(message = "El ID del jugador es obligatorio")
    @Column(name = "id_jugador", nullable = false, unique = true)
    private Long idJugador;

    @Min(value = 0, message = "El saldo no puede ser negativo")
    @Column(name = "saldo")
    private Integer saldo = 0;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion = LocalDateTime.now();
}
