package com.example.perfil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "jugador_faccion")
@Data
public class JugadorFaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "La facción es obligatoria")
    private Long faccionId;

    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDateTime fechaIngreso = LocalDateTime.now();
}