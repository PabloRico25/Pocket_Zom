package com.example.Pocket_Z.modulo_ms.billetera.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "carteras")
@Data
public class Cartera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long jugadorId;   // referencia lógica a Jugador (MS Perfiles)

    private Integer saldo = 0;
    private LocalDateTime ultimaActualizacion = LocalDateTime.now();
}