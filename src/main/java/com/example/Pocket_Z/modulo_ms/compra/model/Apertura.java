package com.example.Pocket_Z.modulo_ms.compra.model;

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

    @Column(nullable = false)
    private Long jugadorId;   // referencia lógica a Jugador

    @ManyToOne
    @JoinColumn(name = "suministro_id")
    private Suministro suministro;

    private LocalDateTime fecha = LocalDateTime.now();
    private String cartasObtenidas;   // JSON con lista de códigos de carta, ej: "[\"ZMB-001\",\"ZMB-002\"]"
}
