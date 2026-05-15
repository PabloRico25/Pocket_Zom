package com.example.Pocket_Z.modulo_ms.mazo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "mazos")
@Data
public class Mazo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long jugadorId;       // referencia lógica a Jugador (MS Perfiles)

    private String nombre;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private Boolean esActivo = false;   // solo un mazo activo por jugador
}