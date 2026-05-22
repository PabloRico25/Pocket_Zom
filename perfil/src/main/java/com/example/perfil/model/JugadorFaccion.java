package com.example.perfil.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "jugador_faccion")
@Data
public class JugadorFaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "faccion_id", nullable = false)
    private Faccion faccion;

    private LocalDateTime fechaIngreso = LocalDateTime.now();
}