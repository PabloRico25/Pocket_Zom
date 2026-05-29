package com.example.rango.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clasificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clasificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Long jugadorId;
    private Integer puntosElo = 1000;
    private Integer victorias = 0;
    private Integer derrotas = 0;
    private String rangoActual = "Bronce";
}
