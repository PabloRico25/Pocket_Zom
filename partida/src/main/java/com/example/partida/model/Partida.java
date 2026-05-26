package com.example.partida.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "partidas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jugador1Id;
    private Long jugador2Id;
    private Long mazoJ1Id;
    private Long mazoJ2Id;
    private Long ganadorId;
    private String estado;
    private LocalDateTime fechaInicio = LocalDateTime.now();
    private LocalDateTime fechaFin;
}
