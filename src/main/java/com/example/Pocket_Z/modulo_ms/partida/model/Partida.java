package com.example.Pocket_Z.modulo_ms.partida.model;

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

    @Column(nullable = false)
    private Long jugador1Id;   // referencia lógica a Jugador (MS Perfiles)

    @Column(nullable = false)
    private Long jugador2Id;   // referencia lógica a Jugador

    @Column(nullable = false)
    private Long mazoJ1Id;     // referencia lógica a Mazo (MS Mazos)

    @Column(nullable = false)
    private Long mazoJ2Id;     // referencia lógica a Mazo

    private Long ganadorId;    // referencia lógica a Jugador (null si empate o en curso)

    private String estado = "EN_CURSO";  // EN_CURSO, FINALIZADA
    private LocalDateTime fechaInicio = LocalDateTime.now();
    private LocalDateTime fechaFin;
}