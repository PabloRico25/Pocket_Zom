package com.example.Pocket_Z.modulo_ms.rango.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clasificacion")
@Data
public class Clasificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long jugadorId;   // referencia lógica a Jugador (MS Perfiles)

    private Integer puntosElo = 1000;
    private Integer victorias = 0;
    private Integer derrotas = 0;
    private String rangoActual = "Bronce";
}