package com.example.publicacion.model;

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
    @JoinColumn(name = "publicacion_id", nullable = false)
    private Publicacion publicacion;

    @Column(name = "comprador_id", nullable = false)
    private Long compradorId;

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra = LocalDateTime.now();
}