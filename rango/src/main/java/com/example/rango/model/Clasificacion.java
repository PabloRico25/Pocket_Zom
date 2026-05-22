package com.example.rango.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clasificacion")
@Data
public class Clasificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jugador_id", nullable = false, unique = true)
    private Long jugadorId;   // RL a Jugador

    @Column(name = "puntos_elo")
    private Integer puntosElo = 1000;

    private Integer victorias = 0;
    private Integer derrotas = 0;

    @Column(name = "rango_actual")
    private String rangoActual = "Bronce";
}