package com.example.partida.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "partidas")
@Data
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jugador1_id", nullable = false)
    private Long jugador1Id;

    @Column(name = "jugador2_id", nullable = false)
    private Long jugador2Id;

    @Column(name = "mazo_j1_id", nullable = false)
    private Long mazoJ1Id;

    @Column(name = "mazo_j2_id", nullable = false)
    private Long mazoJ2Id;

    private Long ganadorId;

    private String estado; // "EN_CURSO", "FINALIZADA"

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio = LocalDateTime.now();

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
}