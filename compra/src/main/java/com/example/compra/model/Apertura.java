package com.example.compra.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "aperturas")
@Data
public class Apertura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jugador_id", nullable = false)
    private Long jugadorId; // RL a Jugador

    @ManyToOne
    @JoinColumn(name = "suministro_id")
    private Suministro suministro;

    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "cartas_obtenidas", columnDefinition = "TEXT")
    private String cartasObtenidas; // JSON array de códigos de carta
}