package com.example.Pocket_Z.modulo_ms.publicacion.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
@Data
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    @Column(nullable = false)
    private Long compradorId;   // referencia lógica a Jugador

    private LocalDateTime fechaCompra = LocalDateTime.now();
}