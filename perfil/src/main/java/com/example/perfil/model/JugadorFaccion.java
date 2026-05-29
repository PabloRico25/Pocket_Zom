package com.example.perfil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "jugador_faccion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JugadorFaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_jugador_faccion")
    private Long idJugadorFaccion;
    @NotNull(message = "El jugador es obligatorio")

    @Column(name = "id_jugador", nullable = false)
    private Long idJugador;
    @NotNull(message = "La facción es obligatoria")

    @Column(name = "id_faccion", nullable = false)
    private Long idFaccion;
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")

    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso = LocalDateTime.now();
}